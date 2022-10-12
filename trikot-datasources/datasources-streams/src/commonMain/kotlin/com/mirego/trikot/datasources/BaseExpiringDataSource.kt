package com.mirego.trikot.datasources

import com.mirego.trikot.foundation.date.Date

data class ExpiringValue<T>(
    val value: T,
    val expiredEpoch: Long
) {
    fun isExpired(): Boolean = expiredEpoch < Date.now.epoch

    companion object {
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
