package com.mirego.sample.viewmodels.showcase.components.button

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController
import kotlinx.coroutines.flow.flowOf

class ButtonShowcaseViewModelController(i18N: I18N) : VMDViewModelController<ButtonShowcaseViewModel, ButtonShowcaseNavigationDelegate>() {
    override val viewModel: ButtonShowcaseViewModelImpl = ButtonShowcaseViewModelImpl(i18N, viewModelScope)

    override fun onCreate() {
        super.onCreate()
        viewModel.closeButton.setAction { navigationDelegate?.close() }
        viewModel.textButton.bindContent(flowOf(VMDTextContent("Label")))
        viewModel.textButton.setAction { navigationDelegate?.showMessage("Text button tapped !") }
        viewModel.imageButton.setAction { navigationDelegate?.showMessage("Image button tapped !") }
        viewModel.textImageButton.setAction { navigationDelegate?.showMessage("Text image button tapped !") }
        viewModel.textPairButton.setAction { navigationDelegate?.showMessage("Text pair button tapped !") }
    }
}
