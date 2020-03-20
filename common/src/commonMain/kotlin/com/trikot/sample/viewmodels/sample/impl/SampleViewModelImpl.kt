package com.trikot.sample.viewmodels.sample.impl

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.switchMap
import com.mirego.trikot.viewmodels.mutable.MutableButtonViewModel
import com.mirego.trikot.viewmodels.mutable.MutableLabelViewModel
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.trikot.sample.domain.FetchQuoteUseCase
import com.trikot.sample.localization.KWordTranslation
import com.trikot.sample.viewmodels.sample.SampleViewModel

class SampleViewModelImpl(
    fetchQuoteUseCase: FetchQuoteUseCase,
    i18N: I18N
) :
    SampleViewModel {

    private val refreshPublisher = Publishers.behaviorSubject(true)

    override val quoteLabel = MutableLabelViewModel().also {
        it.text = refreshPublisher.switchMap {
            fetchQuoteUseCase.fetchQuote()
        }
    }

    override val refreshButton = MutableButtonViewModel().also {
        it.text = i18N[KWordTranslation.REFRESH_BUTTON].just()
        it.action = ViewModelAction {
            refreshPublisher.value = true
        }.just()
    } }
