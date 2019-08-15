package com.trikot.sample.repositories

import com.mirego.trikot.datasources.DataSourceState
import com.trikot.sample.models.Country
import org.reactivestreams.Publisher

interface SampleGraphqlRepo {
    fun country(code: String): Publisher<DataSourceState<Country>>
}
