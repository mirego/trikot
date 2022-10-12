package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.DataState
import com.mirego.trikot.datasources.flow.extensions.mapValue
import com.mirego.trikot.foundation.date.Date
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

data class ExpiringExecutableFlowDataSourceRequest<T>(
    override val cacheableId: String,
    val expiredInMilliseconds: Long,
    override val requestType: FlowDataSourceRequest.Type = FlowDataSourceRequest.Type.USE_CACHE,
    val block: suspend () -> T
) : FlowDataSourceRequest

data class FlowDataSourceExpiringValue<T>(
    val value: T,
    val expiredEpoch: Long
) {
    fun isExpired(): Boolean = expiredEpoch < Date.now.epoch
}

class ExpiringExecutableFlowDataSource<T>(
    cacheDataSource: FlowDataSource<ExpiringExecutableFlowDataSourceRequest<T>, FlowDataSourceExpiringValue<T>>? = null,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : BaseFlowDataSource<ExpiringExecutableFlowDataSourceRequest<T>, FlowDataSourceExpiringValue<T>>(cacheDataSource, coroutineContext) {

    override fun shouldRead(request: ExpiringExecutableFlowDataSourceRequest<T>, data: DataState<FlowDataSourceExpiringValue<T>, Throwable>): Boolean {
        return when (data) {
            is DataState.Pending -> false
            is DataState.Data -> request.requestType == FlowDataSourceRequest.Type.REFRESH_CACHE || data.value.isExpired()
            is DataState.Error -> true
        }
    }

    override suspend fun internalRead(request: ExpiringExecutableFlowDataSourceRequest<T>) =
        FlowDataSourceExpiringValue(request.block(), Date.now.epoch + request.expiredInMilliseconds)

    fun readValue(request: ExpiringExecutableFlowDataSourceRequest<T>) =
        read(request).mapValue { it.value }
}
