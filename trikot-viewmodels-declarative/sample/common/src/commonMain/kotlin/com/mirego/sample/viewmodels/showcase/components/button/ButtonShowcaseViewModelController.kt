package com.mirego.sample.viewmodels.showcase.components.button

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController

class ButtonShowcaseViewModelController(i18N: I18N) : VMDViewModelController<ButtonShowcaseViewModel, ButtonShowcaseNavigationDelegate>() {
    override val viewModel: ButtonShowcaseViewModelImpl = ButtonShowcaseViewModelImpl(i18N, cancellableManager)

    override fun onCreate() {
        super.onCreate()
        viewModel.closeButton.setAction { navigationDelegate?.close() }
        viewModel.textButton.bindContent(VMDTextContent("Label").just())
        viewModel.textButton.setAction { navigationDelegate?.showMessage("Text button tapped !") }
        viewModel.imageButton.setAction { navigationDelegate?.showMessage("Image button tapped !") }
        viewModel.textImageButton.setAction { navigationDelegate?.showMessage("Text image button tapped !") }
        viewModel.textPairButton.setAction { navigationDelegate?.showMessage("Text pair button tapped !") }
    }
}
