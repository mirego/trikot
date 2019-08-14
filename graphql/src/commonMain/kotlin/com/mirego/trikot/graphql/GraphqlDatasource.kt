package com.mirego.trikot.graphql

import com.mirego.trikot.datasources.BaseDataSource
import com.mirego.trikot.datasources.DataSource
import com.mirego.trikot.datasources.DataSourceRequest
import com.mirego.trikot.http.HttpHeaderProvider
import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.streams.reactive.executable.ExecutablePublisher

typealias GraphqlDataSourceType<T> = DataSource<GraphqlQueryDataSourceRequest<T>, T>

data class GraphqlQueryDataSourceRequest<T>(
    val query: GraphqlQuery<T>,
    override val cachableId: String,
    override val requestType: DataSourceRequest.Type = DataSourceRequest.Type.USE_CACHE
) : DataSourceRequest

class GraphqlDataSource<T>(
    private val graphqlPublisherFactory: GraphqlPublisherFactory,
    cacheDataSource: DataSource<GraphqlQueryDataSourceRequest<T>, T>? = null,
    private val httpHeaderProvider: HttpHeaderProvider = HttpConfiguration.defaultHttpHeaderProvider
) : BaseDataSource<GraphqlQueryDataSourceRequest<T>, T>(cacheDataSource) {
    override fun internalRead(request: GraphqlQueryDataSourceRequest<T>): ExecutablePublisher<T> {
        return graphqlPublisherFactory.create(
            request.query,
            httpHeaderProvider = httpHeaderProvider
        )
    }
}
