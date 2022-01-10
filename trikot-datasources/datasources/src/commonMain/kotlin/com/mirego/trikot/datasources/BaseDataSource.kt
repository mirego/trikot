package com.mirego.trikot.datasources

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.streams.StreamsConfiguration
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.RefreshablePublisher
import com.mirego.trikot.streams.reactive.executable.ExecutablePublisher
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.observeOn
import com.mirego.trikot.streams.reactive.onErrorReturn
import com.mirego.trikot.streams.reactive.shared
import com.mirego.trikot.streams.reactive.subscribeOn
import com.mirego.trikot.streams.reactive.switchMap
import com.mirego.trikot.streams.reactive.withCancellableManager
import org.reactivestreams.Publisher

typealias DataSourcePublisherPair<T> = Pair<RefreshablePublisher<DataState<T, Throwable>>, Publisher<DataState<T, Throwable>>>

abstract class BaseDataSource<R : DataSourceRequest, T>(private val cacheDataSource: DataSource<R, T>? = null) :
    DataSource<R, T> {
    private val mapAtomicReference =
        AtomicReference<Map<Any, DataSourcePublisherPair<T>>>(HashMap())

    override fun read(request: R): Publisher<DataState<T, Throwable>> {
        val refreshablePublisher = getRefreshablePublisherPair(request)
        if (request.requestType == DataSourceRequest.Type.REFRESH_CACHE) {
            refreshablePublisher.refreshablePublisher().refresh()
        }
        return refreshablePublisher.sharedPublisher()
    }

    private fun getRefreshablePublisherPair(request: R): DataSourcePublisherPair<T> {
        val cacheableId = request.cacheableId
        val publisherPair = mapAtomicReference.value[cacheableId]
        return when {
            publisherPair != null -> publisherPair
            else -> {
                val publisher =
                    DataStateRefreshablePublisher(
                        { cancellableManager, isRefreshing ->
                            when {
                                isRefreshing || cacheDataSource == null -> readDataOrFallbackToCacheOnError(
                                    request,
                                    cancellableManager
                                )
                                else -> readDataFromCache(cacheDataSource, request)
                            }
                        },
                        DataState.pending()
                    )

                savePublisherToRegistry(cacheableId, publisher, request)
            }
        }
    }

    fun invalidate() {
        mapAtomicReference.value.forEach { (_, value) ->
            value.refreshablePublisher().refresh()
        }
    }

    fun cacheableIds(): List<Any> {
        return mapAtomicReference.value.keys.toList()
    }

    fun clean(cacheableId: Any) {
        clean(listOf(cacheableId))
    }

    fun clean(cacheableIds: List<Any>) {
        cacheableIds.map { id ->
            cacheDataSource?.delete(id)
        }

        val initialMap = mapAtomicReference.value
        val toMutableMap = initialMap.toMutableMap()
        cacheableIds.map { id ->
            toMutableMap.remove(id)
        }

        mapAtomicReference.compareAndSet(initialMap, toMutableMap)
    }

    fun cleanAll() {
        mapAtomicReference.value.keys.map {
            cacheDataSource?.delete(it)
        }
        mapAtomicReference.compareAndSet(mapAtomicReference.value, mapOf())
    }

    protected fun refreshPublisherWithId(cacheableId: Any) {
        mapAtomicReference.value[cacheableId]?.refreshablePublisher()?.refresh()
    }

    private fun readDataOrFallbackToCacheOnError(
        request: R,
        cancellableManager: CancellableManager
    ): Publisher<DataState<T, Throwable>> {
        return readData(request, cancellableManager).switchMap { previousDataState ->
            when {
                previousDataState is DataState.Error && cacheDataSource != null ->
                    cacheDataSource.read(
                        request
                    ).map {
                        when (it) {
                            is DataState.Pending -> {
                                DataState.pending(it.value)
                            }
                            is DataState.Data -> {
                                DataState.error(previousDataState.error, it.value)
                            }
                            else -> {
                                previousDataState
                            }
                        }
                    }
                else -> Publishers.behaviorSubject(previousDataState)
            }
        }
    }

    private fun readDataFromCache(
        cacheDataSource: DataSource<R, T>,
        request: R
    ): Publisher<DataState<T, Throwable>> {
        return cacheDataSource.read(request).withCancellableManager()
            .switchMap { (cancellableManager, dataState) ->
                when {
                    !dataState.isPending() && !dataState.hasData() || dataState.isError() -> readData(
                        request,
                        cancellableManager
                    )
                    else -> Publishers.behaviorSubject(dataState)
                }
            }
    }

    private fun savePublisherToRegistry(
        cacheableId: Any,
        publisher: RefreshablePublisher<DataState<T, Throwable>>,
        request: R
    ): DataSourcePublisherPair<T> {
        val initialMap = mapAtomicReference.value
        val toMutableMap = initialMap.toMutableMap()
        val publisherPair = publisher to publisher.shared()
        toMutableMap[cacheableId] = publisherPair
        return when {
            mapAtomicReference.compareAndSet(initialMap, toMutableMap.toMap()) -> publisherPair
            else -> getRefreshablePublisherPair(request)
        }
    }

    private fun readData(
        request: R,
        cancellableManager: CancellableManager
    ): Publisher<DataState<T, Throwable>> {
        return internalRead(request)
            .also {
                cancellableManager.add(it)
                it.execute()
            }
            .map { readCacheResult ->
                cacheDataSource?.save(request, readCacheResult)
                DataState.data<T, Throwable>(readCacheResult)
            }
            .onErrorReturn { throwable ->
                DataState.error<T, Throwable>(throwable)
            }
            .subscribeOn(StreamsConfiguration.serialSubscriptionDispatchQueue)
            .observeOn(StreamsConfiguration.publisherExecutionDispatchQueue)
    }

    abstract fun internalRead(request: R): ExecutablePublisher<T>

    override fun save(request: R, data: T?) {}

    override fun delete(cacheableId: Any) {}
}

fun <T> DataSourcePublisherPair<T>.refreshablePublisher(): RefreshablePublisher<DataState<T, Throwable>> {
    return first
}

fun <T> DataSourcePublisherPair<T>.sharedPublisher(): Publisher<DataState<T, Throwable>> {
    return second
}
