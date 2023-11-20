package com.mirego.sample.viewmodels.tv

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController

class HomeTvViewModelController(i18N: I18N) : VMDViewModelController<HomeTvViewModel, HomeTvNavigationDelegate>() {
    override val viewModel = HomeTvViewModelImpl(i18N, viewModelScope)
}
