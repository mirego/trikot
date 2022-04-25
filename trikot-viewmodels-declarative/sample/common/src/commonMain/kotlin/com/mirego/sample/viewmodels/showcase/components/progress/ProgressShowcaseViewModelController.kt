package com.mirego.sample.viewmodels.showcase.components.progress

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController

class ProgressShowcaseViewModelController(i18N: I18N) : VMDViewModelController<ProgressShowcaseViewModel, ProgressShowcaseNavigationDelegate>() {
    override val viewModel: ProgressShowcaseViewModelImpl = ProgressShowcaseViewModelImpl(i18N, cancellableManager)

    override fun onCreate() {
        super.onCreate()
        viewModel.closeButton.setAction { navigationDelegate?.close() }
    }
}
