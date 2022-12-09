package com.mirego.sample.viewmodels.showcase.components.list

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController

class ListShowcaseViewModelController(i18N: I18N) : VMDViewModelController<ListShowcaseViewModel, ListShowcaseNavigationDelegate>() {
    override val viewModel: ListShowcaseViewModelImpl = ListShowcaseViewModelImpl(i18N, viewModelScope)

    override fun onCreate() {
        super.onCreate()
        viewModel.closeButton.setAction { navigationDelegate?.close() }
    }
}
