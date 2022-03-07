package com.mirego.sample.viewmodels.showcase.textfield

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class TextFieldShowcaseViewModelPreview : VMDViewModelImpl(CancellableManager()), TextFieldShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponents.Text.withContent("TextField showcase", cancellableManager)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, cancellableManager)

    override val textField = VMDComponents.TextField.empty(cancellableManager) {
        text = "Hello world!"
    }
    override val characterCountText = VMDComponents.Text.withContent("There is 12 characters in text field.", cancellableManager)
    override val clearButton = VMDComponents.Button.withText("Clear", cancellableManager)
}