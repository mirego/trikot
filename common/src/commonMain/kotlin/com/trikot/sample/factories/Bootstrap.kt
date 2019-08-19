package com.trikot.sample.factories

import com.mirego.trikot.datasources.MemoryCacheDataSource
import com.mirego.trikot.graphql.GraphqlDataSource
import com.mirego.trikot.graphql.GraphqlPublisherFactoryImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KWord
import com.mirego.trikot.foundation.concurrent.freeze
import com.trikot.sample.domain.impl.FetchFromGraphqlRepoUseCaseImpl
import com.trikot.sample.repositories.impl.SampleGraphqlRepoImpl

class Bootstrap {
    private val memoryCachedSampleGraphqlRepo = freeze(
        SampleGraphqlRepoImpl(
            GraphqlDataSource(
                GraphqlPublisherFactoryImpl(),
                MemoryCacheDataSource()
            )
        )
    )

    val fetchFromGraphqlRepoUseCase =
        FetchFromGraphqlRepoUseCaseImpl(memoryCachedSampleGraphqlRepo)

    val i18N: I18N = KWord

    val viewModelFactory: ViewModelFactory =
        ViewModelFactoryImpl(this)
}
