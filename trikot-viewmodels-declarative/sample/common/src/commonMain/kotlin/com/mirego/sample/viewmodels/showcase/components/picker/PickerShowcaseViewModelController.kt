package com.mirego.sample.viewmodels.showcase.components.picker

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController

class PickerShowcaseViewModelController(i18N: I18N) : VMDViewModelController<PickerShowcaseViewModel, PickerShowcaseNavigationDelegate>() {
    override val viewModel = PickerShowcaseViewModelImpl(i18N, cancellableManager)

    override fun onCreate() {
        super.onCreate()
        viewModel.closeButton.setAction { navigationDelegate?.close() }
    }
}
