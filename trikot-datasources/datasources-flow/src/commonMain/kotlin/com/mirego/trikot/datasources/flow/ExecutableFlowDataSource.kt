package com.mirego.trikot.datasources.flow

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

typealias ExecutableDataSourceType<T> = FlowDataSource<ExecutableFlowDataSourceRequest<T>, T>

data class ExecutableFlowDataSourceRequest<T : Any>(
    override val cacheableId: String,
    override val requestType: FlowDataSourceRequest.Type = FlowDataSourceRequest.Type.USE_CACHE,
    val block: suspend () -> T
) : FlowDataSourceRequest

class ExecutableFlowDataSource<T : Any>(
    cacheDataSource: FlowDataSource<ExecutableFlowDataSourceRequest<T>, T>? = null,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : BaseFlowDataSource<ExecutableFlowDataSourceRequest<T>, T>(cacheDataSource, coroutineContext) {
    override suspend fun internalRead(request: ExecutableFlowDataSourceRequest<T>): T = request.block()
}
