package com.trikot.sample.domain.impl

import com.mirego.trikot.datasources.DataSourceState
import com.mirego.trikot.streams.Configuration
import com.mirego.trikot.streams.reactive.shared
import com.mirego.trikot.streams.reactive.subscribeOn
import com.trikot.sample.domain.FetchFromGraphqlRepoUseCase
import com.trikot.sample.models.Country
import com.trikot.sample.repositories.impl.SampleGraphqlRepoImpl
import org.reactivestreams.Publisher

class FetchFromGraphqlRepoUseCaseImpl(private val graphqlRepo: SampleGraphqlRepoImpl) :
    FetchFromGraphqlRepoUseCase {
    override fun fetchCanadianCountry(): Publisher<DataSourceState<Country>> {
        return graphqlRepo.country("CA")
            .subscribeOn(Configuration.serialSubscriptionDispatchQueue)
            .shared()
    }
}
