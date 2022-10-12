package com.mirego.trikot.datasources.flow

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MemoryCacheFlowDataSource<R : FlowDataSourceRequest, T>(
    upstreamDataSource: FlowDataSource<R, T>? = null,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : BaseFlowDataSource<R, T>(upstreamDataSource, coroutineContext) {
    private val memoryCache: MutableMap<Any, T?> = mutableMapOf()

    override suspend fun internalRead(request: R): T {
        return memoryCache[request.cacheableId] ?: throw NoSuchElementException()
    }

    override suspend fun save(request: R, data: T?) {
        memoryCache[request.cacheableId] = data
    }

    override suspend fun delete(cacheableId: String) {
        memoryCache.remove(cacheableId)
    }

    override suspend fun clear() {
        memoryCache.clear()
    }
}
