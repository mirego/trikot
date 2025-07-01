package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.DataState
import com.mirego.trikot.datasources.flow.extensions.mapValue
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

interface ExpiringFlowDataSourceRequest : FlowDataSourceRequest {
    val expiredInMilliseconds: Long
}

@OptIn(ExperimentalTime::class)
data class FlowDataSourceExpiringValue<T>(
    val value: T,
    val expiredEpoch: Long
) {
    fun isExpired(): Boolean = expiredEpoch < Clock.System.now().toEpochMilliseconds()
}

abstract class BaseExpiringExecutableFlowDataSource<R : ExpiringFlowDataSourceRequest, T>(
    cacheDataSource: FlowDataSource<R, FlowDataSourceExpiringValue<T>>? = null,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : BaseFlowDataSource<R, FlowDataSourceExpiringValue<T>>(cacheDataSource, coroutineContext) {

    override fun shouldRead(request: R, data: DataState<FlowDataSourceExpiringValue<T>, Throwable>): Boolean {
        return when (data) {
            is DataState.Pending -> false
            is DataState.Data -> request.requestType == FlowDataSourceRequest.Type.REFRESH_CACHE || data.value.isExpired()
            is DataState.Error -> true
        }
    }

    fun readValue(request: R) = read(request).mapValue { it.value }
}
