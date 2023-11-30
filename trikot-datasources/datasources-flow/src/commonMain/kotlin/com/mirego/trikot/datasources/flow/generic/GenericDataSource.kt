package com.mirego.trikot.datasources.flow.generic

import com.mirego.trikot.datasources.flow.BaseExpiringExecutableFlowDataSource
import com.mirego.trikot.datasources.flow.DataSourceUtils
import com.mirego.trikot.datasources.flow.ExpiringFlowDataSourceRequest
import com.mirego.trikot.datasources.flow.FlowDataSourceRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.minutes

open class GenericDataSource<T>(
    json: Json,
    dataSerializer: KSerializer<T>,
    diskCachePath: String?
) : BaseExpiringExecutableFlowDataSource<GenericDataSourceRequest<T>, T>(
    diskCachePath?.let { GenericDiskDataSource(json, dataSerializer, diskCachePath) }
) {
    override suspend fun internalRead(request: GenericDataSourceRequest<T>) = DataSourceUtils.buildExpiringValue(request.block(), request)
}

data class GenericDataSourceRequest<T>(
    override val cacheableId: String,
    override val expiredInMilliseconds: Long = 1.minutes.inWholeMilliseconds,
    override val requestType: FlowDataSourceRequest.Type = FlowDataSourceRequest.Type.USE_CACHE,
    val block: suspend () -> T
) : ExpiringFlowDataSourceRequest
