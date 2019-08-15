package com.trikot.sample.domain

import com.mirego.trikot.datasources.DataSourceState
import com.trikot.sample.models.Country
import org.reactivestreams.Publisher

interface FetchFromGraphqlRepoUseCase {
    fun fetchCanadianCountry(): Publisher<DataSourceState<Country>>
}
