package com.mirego.sample.viewmodels.showcase.components.button

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.MainScope

class ButtonShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), ButtonShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Button Showcase", coroutineScope)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, coroutineScope)

    override val textButtonTitle = VMDComponents.Text.withContent("Text button", coroutineScope)
    override val textButton = VMDComponents.Button.withText("Label", coroutineScope)

    override val imageButtonTitle = VMDComponents.Text.withContent("Image button", coroutineScope)
    override val imageButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, coroutineScope)

    override val textImageButtonTitle = VMDComponents.Text.withContent("Text image button", coroutineScope)
    override val textImageButton = VMDComponents.Button.withTextImage(VMDTextImagePairContent("Label", SampleImageResource.ICON_CLOSE), coroutineScope)

    override val textPairButtonTitle = VMDComponents.Text.withContent("Text pair button", coroutineScope)
    override val textPairButton = VMDComponents.Button.withTextPair(VMDTextPairContent("Label", "Label"), coroutineScope)
}
