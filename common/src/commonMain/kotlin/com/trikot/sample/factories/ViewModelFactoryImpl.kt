package com.trikot.sample.factories

import com.trikot.sample.viewmodels.sample.SampleViewModel
import com.trikot.sample.viewmodels.sample.impl.SampleViewModelImpl

class ViewModelFactoryImpl(private val bootstrap: Bootstrap) :
    ViewModelFactory {
    override val sampleViewModel: SampleViewModel get() = SampleViewModelImpl(bootstrap.fetchFromGraphqlRepoUseCase, bootstrap.i18N)
}
