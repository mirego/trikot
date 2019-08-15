package com.trikot.sample.viewmodels.sample.impl

import com.mirego.trikot.metaviews.MetaButton
import com.mirego.trikot.metaviews.MetaLabel
import com.mirego.trikot.metaviews.mutable.MutableMetaButton
import com.mirego.trikot.metaviews.mutable.MutableMetaLabel
import com.trikot.sample.domain.FetchFromGraphqlRepoUseCase
import com.trikot.sample.viewmodels.sample.SampleViewModel

class SampleViewModelImpl(fetchFromGraphqlRepoUseCase: FetchFromGraphqlRepoUseCase):
    SampleViewModel {
    override val helloWorldLabel = MutableMetaLabel().also {
    }
    override val button = MutableMetaButton()
}
