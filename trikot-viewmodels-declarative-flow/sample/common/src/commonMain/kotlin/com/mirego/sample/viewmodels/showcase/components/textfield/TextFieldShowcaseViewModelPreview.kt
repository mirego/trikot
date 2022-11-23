package com.mirego.sample.viewmodels.showcase.components.textfield

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithText
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import com.mirego.trikot.viewmodels.declarative.viewmodel.textField
import kotlinx.coroutines.MainScope

class TextFieldShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), TextFieldShowcaseViewModel {
    override val title: VMDTextViewModel = text("TextField showcase")
    override val closeButton = buttonWithImage(SampleImageResource.ICON_CLOSE)

    override val textField = textField("Hello world!")
    override val characterCountText = text("There is 12 characters in text field.")
    override val clearButton = buttonWithText("Clear")
}
