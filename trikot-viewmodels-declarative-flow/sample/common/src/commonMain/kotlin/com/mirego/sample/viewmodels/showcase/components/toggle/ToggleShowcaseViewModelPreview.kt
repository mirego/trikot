package com.mirego.sample.viewmodels.showcase.components.toggle

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.MainScope

class ToggleShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), ToggleShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Text Showcase", coroutineScope)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, coroutineScope)

    override val checkboxTitle = VMDComponents.Text.withContent("Checkbox", coroutineScope)
    override val textCheckboxTitle = VMDComponents.Text.withContent("Text checkbox", coroutineScope)
    override val imageCheckboxTitle = VMDComponents.Text.withContent("Image checkbox", coroutineScope)
    override val textImageCheckboxTitle = VMDComponents.Text.withContent("Text image checkbox", coroutineScope)
    override val textPairCheckboxTitle = VMDComponents.Text.withContent("Text pair checkbox", coroutineScope)
    override val switchTitle = VMDComponents.Text.withContent("Switch", coroutineScope)
    override val textSwitchTitle = VMDComponents.Text.withContent("Text switch", coroutineScope)
    override val imageSwitchTitle = VMDComponents.Text.withContent("Image switch", coroutineScope)
    override val textImageSwitchTitle = VMDComponents.Text.withContent("Text image switch", coroutineScope)
    override val textPairSwitchTitle = VMDComponents.Text.withContent("Text pair switch", coroutineScope)

    override val emptyToggle = VMDComponents.Toggle.empty(coroutineScope)
    override val textToggle = VMDComponents.Toggle.withText("Label", false, coroutineScope)
    override val imageToggle = VMDComponents.Toggle.withImage(SampleImageResource.ICON_CLOSE, false, coroutineScope)
    override val textImageToggle = VMDComponents.Toggle.withTextImage(VMDTextImagePairContent("Label", SampleImageResource.ICON_CLOSE), false, coroutineScope)
    override val textPairToggle = VMDComponents.Toggle.withTextPair(VMDTextPairContent("Label", "Subtitle"), false, coroutineScope)
}
