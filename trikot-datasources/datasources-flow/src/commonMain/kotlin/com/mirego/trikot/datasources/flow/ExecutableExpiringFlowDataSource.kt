package com.mirego.trikot.datasources.flow

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class ExpiringExecutableFlowDataSourceRequest<T>(
    override val cacheableId: String,
    override val expiredInMilliseconds: Long,
    override val requestType: FlowDataSourceRequest.Type = FlowDataSourceRequest.Type.USE_CACHE,
    val block: suspend () -> T
) : ExpiringFlowDataSourceRequest

@OptIn(ExperimentalTime::class)
open class ExpiringExecutableFlowDataSource<T>(
    cacheDataSource: FlowDataSource<ExpiringExecutableFlowDataSourceRequest<T>, FlowDataSourceExpiringValue<T>>? = null,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : BaseExpiringExecutableFlowDataSource<ExpiringExecutableFlowDataSourceRequest<T>, T>(cacheDataSource, coroutineContext) {

    override suspend fun internalRead(request: ExpiringExecutableFlowDataSourceRequest<T>) =
        FlowDataSourceExpiringValue(request.block(), Clock.System.now().toEpochMilliseconds() + request.expiredInMilliseconds)
}
