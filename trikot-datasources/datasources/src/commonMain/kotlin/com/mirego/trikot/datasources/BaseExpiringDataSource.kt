package com.mirego.trikot.datasources

import com.mirego.trikot.foundation.date.Date
import kotlin.time.ExperimentalTime

data class ExpiringValue<T>(
    val value: T,
    val expiredEpoch: Long
) {
    @ExperimentalTime
    fun isExpired(): Boolean = expiredEpoch < Date.now.epoch

    companion object {
        @ExperimentalTime
        fun <T> build(value: T, request: ExpiringDataSourceRequest): ExpiringValue<T> =
            ExpiringValue(value, Date.now.epoch + request.expiredInMilliseconds)
    }
}

interface ExpiringDataSourceRequest : DataSourceRequest {
    val expiredInMilliseconds: Long
}

abstract class BaseExpiringDataSource<R : ExpiringDataSourceRequest, D, T>(
    cacheDataSource: DataSource<R, T>? = null
) : BaseHotDataSource<R, T>(cacheDataSource) where T : ExpiringValue<D> {

    @ExperimentalTime
    override fun internalShouldRead(request: R, data: DataState<T, Throwable>): Boolean {
        return when (data) {
            is DataState.Pending -> false
            is DataState.Data -> request.requestType == DataSourceRequest.Type.REFRESH_CACHE || data.value.isExpired()
            is DataState.Error -> true
        }
    }

    override fun delete(cacheableId: Any) {
    }
}
