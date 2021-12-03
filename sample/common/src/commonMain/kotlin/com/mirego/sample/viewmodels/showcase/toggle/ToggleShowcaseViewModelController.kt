package com.mirego.sample.viewmodels.showcase.toggle

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController

class ToggleShowcaseViewModelController(i18N: I18N) : VMDViewModelController<ToggleShowcaseViewModel, ToggleShowcaseNavigationDelegate>() {
    override val viewModel: ToggleShowcaseViewModelImpl = ToggleShowcaseViewModelImpl(i18N, cancellableManager)

    override fun onCreate() {
        super.onCreate()
        viewModel.closeButton.setAction { navigationDelegate?.close() }
    }
}
