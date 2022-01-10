package com.mirego.sample.viewmodels.showcase.text

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController

class TextShowcaseViewModelController(i18N: I18N) : VMDViewModelController<TextShowcaseViewModel, TextShowcaseNavigationDelegate>() {
    override val viewModel: TextShowcaseViewModelImpl = TextShowcaseViewModelImpl(i18N, cancellableManager)

    override fun onCreate() {
        super.onCreate()
        viewModel.closeButton.setAction { navigationDelegate?.close() }
    }
}
