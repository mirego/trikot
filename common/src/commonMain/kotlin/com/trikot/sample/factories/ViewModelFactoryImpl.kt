package com.trikot.sample.factories

import com.mirego.trikot.foundation.concurrent.freeze
import com.trikot.sample.domain.impl.FetchQuoteUseCaseImpl
import com.trikot.sample.repositories.impl.QuoteRepositoryImpl
import com.trikot.sample.viewmodels.sample.SampleViewModel
import com.trikot.sample.viewmodels.sample.impl.SampleViewModelImpl

class ViewModelFactoryImpl(private val bootstrap: Bootstrap) :
    ViewModelFactory {
    override val sampleViewModel: SampleViewModel get() = freeze(SampleViewModelImpl(FetchQuoteUseCaseImpl(QuoteRepositoryImpl()), bootstrap.i18N))
}
