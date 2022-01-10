package com.trikot.sample.factories

import com.mirego.trikot.kword.I18N
import com.trikot.sample.domain.impl.FetchQuoteUseCaseImpl
import com.trikot.sample.repositories.impl.QuoteRepositoryImpl
import com.trikot.sample.viewmodels.home.HomeViewModelController

class ViewModelControllerFactoryImpl(private val i18N: I18N) :
    ViewModelControllerFactory {
    override fun createHome(): HomeViewModelController =
        HomeViewModelController(FetchQuoteUseCaseImpl(QuoteRepositoryImpl()), i18N)
}
