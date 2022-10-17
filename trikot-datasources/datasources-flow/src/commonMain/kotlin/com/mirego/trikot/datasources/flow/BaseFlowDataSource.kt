package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.DataState
import com.mirego.trikot.datasources.extensions.value
import com.mirego.trikot.datasources.flow.extensions.withPreviousDataStateValue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseFlowDataSource<R : FlowDataSourceRequest, T>(
    private val upstreamDataSource: FlowDataSource<R, T>? = null,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : FlowDataSource<R, T> {

    private val cache: MutableMap<String, CachedDataFlow<R, T>> = mutableMapOf()
    private val coroutineScope = CoroutineScope(coroutineContext)
    private val cacheMutex = Mutex()

    final override fun read(request: R): Flow<DataState<T, Throwable>> {
        val cachedFlow = cache[request.cacheableId]
        return readIfNeeded(request, cachedFlow)
    }

    private fun readIfNeeded(request: R, flow: CachedDataFlow<R, T>?) =
        flow {
            val innerFlow = cacheMutex.withLock {
                if (flow != null) {
                    if (shouldRead(request, flow.value)) {
                        flow.retry(request)
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

    private fun readInFlow(initialRequest: R) =
        CachedDataFlow(coroutineScope, initialRequest = initialRequest) { request ->
            flow {
                val upstreamRead = upstreamDataSource?.read(request)
                if (upstreamRead != null) {
                    upstreamRead.collect {
                        when (it) {
                            is DataState.Pending -> emit(it)
                            is DataState.Data -> if (shouldRead(request, it)) {
                                emitInternalRead(request, it.value)
                            } else {
                                emit(it)
                            }
                            is DataState.Error -> emitInternalRead(request, it.value)
                        }
                    }
                } else {
                    emitInternalRead(request)
                }
            }
        }

    private suspend fun FlowCollector<DataState<T, Throwable>>.emitInternalRead(request: R, oldValue: T? = null) {
        try {
            emit(DataState.pending(oldValue))
            val readResult = internalRead(request)
            upstreamDataSource?.save(request, readResult)
            emit(DataState.Data(readResult))
        } catch (throwable: Throwable) {
            if (throwable is CancellationException) {
                throw throwable
            }
            emit(DataState.Error(throwable))
        }
    }

    protected abstract suspend fun internalRead(request: R): T

    protected open fun shouldRead(request: R, data: DataState<T, Throwable>): Boolean {
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
        upstreamDataSource?.delete(cacheableId)
        cacheMutex.withLock {
            cache.remove(cacheableId)
        }
    }

    override suspend fun clear() {
        upstreamDataSource?.clear()
        cacheMutex.withLock {
            cache.clear()
        }
    }

    internal fun cacheableIds(): List<Any> {
        return cache.keys.toList()
    }
}

private class CachedDataFlow<R : FlowDataSourceRequest, T>(
    scope: CoroutineScope,
    initialRequest: R,
    initialValue: DataState<T, Throwable> = DataState.pending(),
    private val flowBlock: (request: R) -> Flow<DataState<T, Throwable>>
) : StateFlow<DataState<T, Throwable>> {
    private val retryCount = MutableStateFlow(RetryData(initialRequest, 0))
    private val dataStateFlow = MutableStateFlow(initialValue)

    private val blockData: Flow<DataState<T, Throwable>> =
        retryCount.flatMapLatest { flowBlock(it.request) }
            .withPreviousDataStateValue()

    init {
        scope.launch {
            // Start subscribing only when dataStateFlow has subscriptions
            dataStateFlow.subscriptionCount.first { it > 0 }
            blockData.collect {
                dataStateFlow.value = it
            }
        }
    }

    fun retry(request: R) {
        dataStateFlow.value = DataState.pending(dataStateFlow.value.value())
        retryCount.value = RetryData(request, retryCount.value.count + 1)
    }

    override val replayCache: List<DataState<T, Throwable>>
        get() = dataStateFlow.replayCache

    override val value: DataState<T, Throwable>
        get() = dataStateFlow.value

    override suspend fun collect(collector: FlowCollector<DataState<T, Throwable>>): Nothing {
        dataStateFlow.collect(collector)
    }

    private data class RetryData<R : FlowDataSourceRequest>(
        val request: R,
        val count: Int
    )
}
