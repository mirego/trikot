package com.mirego.sample.viewmodels.tv

import com.mirego.sample.KWordTranslation
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeTvViewModelController(i18N: I18N): VMDViewModelController<HomeTvViewModel, HomeTvNavigationDelegate>() {
    override val viewModel: HomeTvViewModel =  object : VMDViewModelImpl(viewModelScope), HomeTvViewModel {
        override val title = i18N[KWordTranslation.HOME_TITLE]
    }
}
