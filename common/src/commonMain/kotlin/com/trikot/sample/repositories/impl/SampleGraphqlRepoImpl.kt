package com.trikot.sample.repositories.impl

import com.mirego.trikot.datasources.DataSourceState
import com.mirego.trikot.graphql.GraphqlDataSourceType
import com.mirego.trikot.graphql.GraphqlQueryDataSourceRequest
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.mirego.trikot.streams.reactive.map
import com.trikot.sample.models.Country
import com.trikot.sample.models.CountryResponse
import com.trikot.sample.models.DataResponse
import com.trikot.sample.repositories.SampleGraphqlRepo
import com.trikot.sample.repositories.datasources.queries.SampleQuery
import org.reactivestreams.Publisher

class SampleGraphqlRepoImpl(private val dataSource: GraphqlDataSourceType<DataResponse<CountryResponse>>) :
    SampleGraphqlRepo {
    override fun country(code: String): Publisher<DataSourceState<Country>> {
        val cachableId = "cache-$code"

        return dataSource.read(GraphqlQueryDataSourceRequest(
            SampleQuery(
                code
            ), cachableId))
            .distinctUntilChanged()
            .map { DataSourceState(it.isLoading, it.data?.data?.country) }
    }
}
