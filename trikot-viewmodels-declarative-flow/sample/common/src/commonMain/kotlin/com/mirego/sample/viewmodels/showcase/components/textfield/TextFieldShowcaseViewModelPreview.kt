package com.mirego.sample.viewmodels.showcase.components.textfield

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.MainScope

class TextFieldShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), TextFieldShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponents.Text.withContent("TextField showcase", coroutineScope)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, coroutineScope)

    override val textField = VMDComponents.TextField.empty(coroutineScope) {
        text = "Hello world!"
    }
    override val characterCountText = VMDComponents.Text.withContent("There is 12 characters in text field.", coroutineScope)
    override val clearButton = VMDComponents.Button.withText("Clear", coroutineScope)
}
