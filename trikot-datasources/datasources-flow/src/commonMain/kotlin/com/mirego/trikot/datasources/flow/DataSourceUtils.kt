package com.mirego.trikot.datasources.flow

import com.mirego.trikot.foundation.date.Date

object DataSourceUtils {
    fun <T> buildExpiringValue(value: T, request: ExpiringFlowDataSourceRequest) = FlowDataSourceExpiringValue(value, Date.now.epoch + request.expiredInMilliseconds)
}
