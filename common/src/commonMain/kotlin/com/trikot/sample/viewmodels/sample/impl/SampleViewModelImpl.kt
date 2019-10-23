package com.trikot.sample.viewmodels.sample.impl

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.metaviews.mutable.MutableMetaButton
import com.mirego.trikot.metaviews.mutable.MutableMetaLabel
import com.mirego.trikot.metaviews.properties.MetaAction
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.switchMap
import com.trikot.sample.domain.FetchQuoteUseCase
import com.trikot.sample.localization.KWordTranslation
import com.trikot.sample.viewmodels.sample.SampleViewModel

class SampleViewModelImpl(
    fetchQuoteUseCase: FetchQuoteUseCase,
    i18N: I18N
) :
    SampleViewModel {

    override val quoteLabel = MutableMetaLabel().also {
        it.text = "PLACEHOLDER".just()
    }

    override val refreshButton = MutableMetaButton()
}
