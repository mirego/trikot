package com.mirego.sample.viewmodels.showcase.components.snackbar

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController

class SnackbarShowcaseViewModelController(i18N: I18N) : VMDViewModelController<SnackbarShowcaseViewModel, SnackbarShowcaseNavigationDelegate>() {
    override val viewModel: SnackbarShowcaseViewModelImpl = SnackbarShowcaseViewModelImpl(i18N, viewModelControllerScope)

    override fun onCreate() {
        super.onCreate()
        viewModel.closeButton.setAction { navigationDelegate?.close() }
    }
}
