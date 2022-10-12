package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseFlowDataSource<R : FlowDataSourceRequest, T>(
    private val upstreamDataSource: FlowDataSource<R, T>? = null,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : FlowDataSource<R, T> {

    private val cache: MutableMap<String, RetryableDataFlow<T>> = mutableMapOf()
    private val coroutineScope = CoroutineScope(coroutineContext)
    private val readMutex = Mutex()

    final override fun read(request: R): Flow<DataState<T, Throwable>> {
        val cachedFlow = cache[request.cacheableId]
        return readIfNeeded(request, cachedFlow)
    }

    private fun readIfNeeded(request: R, flow: RetryableDataFlow<T>?) =
        flow {
            val innerFlow = readMutex.withLock {
                if (flow != null) {
                    if (shouldRead(request, flow.value)) {
                        flow.retry()
                    }
                    flow
                } else {
                    readInFlow(request).also {
                        cache[request.cacheableId] = it
                    }
                }
            }

            emitAll(innerFlow)
        }

    private fun readInFlow(request: R, currentValue: T? = null) =
        RetryableDataFlow(coroutineScope) {
            flow {
                try {
                    emit(DataState.Pending(currentValue))
                    val readResult = internalRead(request)
                    upstreamDataSource?.save(request, readResult)
                    emit(DataState.Data(readResult))
                } catch (throwable: Throwable) {
                    val upstreamRead = upstreamDataSource?.read(request)
                    if (upstreamRead != null) {
                        upstreamRead.collect {
                            when (it) {
                                is DataState.Pending -> emit(it)
                                is DataState.Data -> emit(it)
                                // We emit the current datasource error
                                is DataState.Error -> emit(DataState.Error(throwable, currentValue))
                            }
                        }
                    } else {
                        emit(DataState.Error(throwable, currentValue))
                    }
                }
            }
        }

    abstract suspend fun internalRead(request: R): T

    open fun shouldRead(request: R, data: DataState<T, Throwable>): Boolean {
        return when (data) {
            is DataState.Pending -> false
            is DataState.Data -> request.requestType == FlowDataSourceRequest.Type.REFRESH_CACHE
            is DataState.Error -> true
        }
    }

    override suspend fun save(request: R, data: T?) {
        // No-op
    }

    override suspend fun delete(cacheableId: String) {
        delete(listOf(cacheableId))
    }

    override suspend fun clear() {
        upstreamDataSource?.clear()
        cache.clear()
    }

    private suspend fun delete(cacheableIds: List<String>) {
        cacheableIds.map { id ->
            upstreamDataSource?.delete(id)
            cache.remove(id)
        }
    }

    internal fun cacheableIds(): List<Any> {
        return cache.keys.toList()
    }
}
