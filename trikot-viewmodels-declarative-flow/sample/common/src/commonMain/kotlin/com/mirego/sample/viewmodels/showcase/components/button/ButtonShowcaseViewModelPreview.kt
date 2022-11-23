package com.mirego.sample.viewmodels.showcase.components.button

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithText
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithTextImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithTextPair
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.MainScope

class ButtonShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), ButtonShowcaseViewModel {
    override val title = text("Button Showcase")
    override val closeButton = buttonWithImage(SampleImageResource.ICON_CLOSE)

    override val textButtonTitle = text("Text button")
    override val textButton = buttonWithText("Label")

    override val imageButtonTitle = text("Image button")
    override val imageButton = buttonWithImage(SampleImageResource.ICON_CLOSE)

    override val textImageButtonTitle = text("Text image button")
    override val textImageButton = buttonWithTextImage("Label", SampleImageResource.ICON_CLOSE)

    override val textPairButtonTitle = text("Text pair button")
    override val textPairButton = buttonWithTextPair("Label", "Label")
}
