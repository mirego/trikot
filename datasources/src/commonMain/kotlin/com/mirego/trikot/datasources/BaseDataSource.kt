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

typealias DataSourcePublisherPair<T> = Pair<RefreshablePublisher<DataSourceState<T>>, Publisher<DataSourceState<T>>>

abstract class BaseDataSource<R : DataSourceRequest, T>(private val cacheDataSource: DataSource<R, T>? = null) :
    DataSource<R, T> {
    private val mapAtomicReference =
        AtomicReference<Map<Any, DataSourcePublisherPair<T>>>(HashMap())

    override fun read(request: R): Publisher<DataSourceState<T>> {
        val refreshablePublisher = getRefreshablePublisherPair(request)
        if (request.requestType == DataSourceRequest.Type.REFRESH_CACHE) {
            refreshablePublisher.refreshablePublisher().refresh()
        }
        return refreshablePublisher.sharedPublisher()
    }

    private fun getRefreshablePublisherPair(request: R): DataSourcePublisherPair<T> {
        val cachableId = request.cachableId
        val publisherPair = mapAtomicReference.value[cachableId]
        return when {
            publisherPair != null -> publisherPair
            else -> {
                val publisher =
                    RefreshablePublisher<DataSourceState<T>>({ cancellableManager, isRefreshing ->
                        when {
                            isRefreshing || cacheDataSource == null -> readDataOrFallbackToCacheOnError(
                                request,
                                cancellableManager
                            )
                            else -> readDataFromCache(cacheDataSource, request)
                        }
                    }, DataSourceState(true, null))

                savePublisherToRegistry(cachableId, publisher, request)
            }
        }
    }

    fun invalidate() {
        mapAtomicReference.value.forEach { (_, value) ->
            value.refreshablePublisher().refresh()
        }
    }

    protected fun refreshPublisherWithId(cachableId: Any) {
        mapAtomicReference.value[cachableId]?.refreshablePublisher()?.refresh()
    }

    private fun readDataOrFallbackToCacheOnError(
        request: R,
        cancellableManager: CancellableManager
    ): Publisher<DataSourceState<T>> {
        return readData(request, cancellableManager).switchMap { previousDataSourceState ->
            when {
                previousDataSourceState.error != null && cacheDataSource != null -> cacheDataSource.read(
                    request
                ).map {
                    DataSourceState(false, it.data, previousDataSourceState.error)
                }
                else -> Publishers.behaviorSubject(previousDataSourceState)
            }
        }
    }

    private fun readDataFromCache(
        cacheDataSource: DataSource<R, T>,
        request: R
    ): Publisher<DataSourceState<T>> {
        return cacheDataSource.read(request).withCancellableManager()
            .switchMap { (cancellableManager, dataSourceState) ->
                when {
                    !dataSourceState.isLoading && dataSourceState.data == null || dataSourceState.error != null -> readData(
                        request,
                        cancellableManager
                    )
                    else -> Publishers.behaviorSubject(dataSourceState)
                }
            }
    }

    private fun savePublisherToRegistry(
        cachableId: Any,
        publisher: RefreshablePublisher<DataSourceState<T>>,
        request: R
    ): DataSourcePublisherPair<T> {
        val initialMap = mapAtomicReference.value
        val toMutableMap = initialMap.toMutableMap()
        val publisherPair = publisher to publisher.shared()
        toMutableMap[cachableId] = publisherPair
        return when {
            mapAtomicReference.compareAndSet(initialMap, toMutableMap.toMap()) -> publisherPair
            else -> getRefreshablePublisherPair(request)
        }
    }

    private fun readData(
        request: R,
        cancellableManager: CancellableManager
    ): Publisher<DataSourceState<T>> {
        return internalRead(request)
            .also {
                cancellableManager.add(it)
                it.execute()
            }
            .map { readCacheResult ->
                cacheDataSource?.save(request, readCacheResult)
                DataSourceState(false, readCacheResult)
            }
            .onErrorReturn { throwable ->
                DataSourceState(false, null, throwable)
            }
            .subscribeOn(StreamsConfiguration.serialSubscriptionDispatchQueue)
            .observeOn(StreamsConfiguration.publisherExecutionDispatchQueue)
    }

    abstract fun internalRead(request: R): ExecutablePublisher<T>

    override fun save(request: R, data: T?) {}
}

fun <T> DataSourcePublisherPair<T>.refreshablePublisher(): RefreshablePublisher<DataSourceState<T>> {
    return first
}

fun <T> DataSourcePublisherPair<T>.sharedPublisher(): Publisher<DataSourceState<T>> {
    return second
}
