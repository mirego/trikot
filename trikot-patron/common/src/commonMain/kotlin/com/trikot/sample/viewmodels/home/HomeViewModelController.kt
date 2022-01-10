package com.trikot.sample.viewmodels.home

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.reactive.RefreshablePublisher
import com.trikot.sample.domain.FetchQuoteUseCase
import com.trikot.sample.viewmodels.base.BaseNavigationDelegate
import com.trikot.sample.viewmodels.base.BaseViewModelController
import com.trikot.sample.viewmodels.home.impl.HomeViewModelImpl

class HomeViewModelController(
    fetchQuoteUseCase: FetchQuoteUseCase,
    i18N: I18N
) : BaseViewModelController<BaseNavigationDelegate, HomeViewModel>() {
    private val refreshableQuotePublisher = RefreshablePublisher({ _, _ ->
        fetchQuoteUseCase.fetchQuote()
    })

    override val viewModel: HomeViewModel = HomeViewModelImpl(refreshableQuotePublisher, i18N)
}
