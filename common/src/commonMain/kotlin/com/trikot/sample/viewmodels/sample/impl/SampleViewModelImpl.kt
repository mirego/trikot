package com.trikot.sample.viewmodels.sample.impl

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.metaviews.mutable.MutableMetaButton
import com.mirego.trikot.metaviews.mutable.MutableMetaLabel
import com.trikot.sample.domain.FetchFromGraphqlRepoUseCase
import com.trikot.sample.localization.KWordTranslation
import com.trikot.sample.viewmodels.sample.SampleViewModel

class SampleViewModelImpl(
    fetchFromGraphqlRepoUseCase: FetchFromGraphqlRepoUseCase,
    i18N: I18N
):
    SampleViewModel {
    override val helloWorldLabel = MutableMetaLabel().also {
        it.text.value = i18N[KWordTranslation.HELLO_WORLD]
    }
    override val button = MutableMetaButton()
}
