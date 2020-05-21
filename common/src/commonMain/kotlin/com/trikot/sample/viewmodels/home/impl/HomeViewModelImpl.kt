package com.trikot.sample.viewmodels.home.impl

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.reactive.RefreshablePublisher
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.mutable.MutableButtonViewModel
import com.mirego.trikot.viewmodels.mutable.MutableLabelViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.trikot.sample.localization.KWordTranslation
import com.trikot.sample.viewmodels.home.HomeViewModel

class HomeViewModelImpl(
    refreshableQuotePublisher: RefreshablePublisher<String>,
    i18N: I18N
) :
    HomeViewModel, MutableViewModel() {

    override val quoteLabel = MutableLabelViewModel().also {
        it.text = refreshableQuotePublisher
    }

    override val refreshButton = MutableButtonViewModel().also {
        it.text = i18N[KWordTranslation.REFRESH_BUTTON].just()
        it.action = ViewModelAction {
            refreshableQuotePublisher.refresh()
        }.just()
    } }
