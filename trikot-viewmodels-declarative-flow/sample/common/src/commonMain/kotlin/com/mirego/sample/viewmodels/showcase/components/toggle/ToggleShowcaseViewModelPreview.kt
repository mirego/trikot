package com.mirego.sample.viewmodels.showcase.components.toggle

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import com.mirego.trikot.viewmodels.declarative.viewmodel.toggle
import com.mirego.trikot.viewmodels.declarative.viewmodel.toggleWithImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.toggleWithText
import com.mirego.trikot.viewmodels.declarative.viewmodel.toggleWithTextImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.toggleWithTextPair
import kotlinx.coroutines.MainScope

class ToggleShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), ToggleShowcaseViewModel {
    override val title = text("Text Showcase")
    override val closeButton = buttonWithImage(SampleImageResource.ICON_CLOSE)

    override val checkboxTitle = text("Checkbox")
    override val textCheckboxTitle = text("Text checkbox")
    override val imageCheckboxTitle = text("Image checkbox")
    override val textImageCheckboxTitle = text("Text image checkbox")
    override val textPairCheckboxTitle = text("Text pair checkbox")
    override val switchTitle = text("Switch")
    override val textSwitchTitle = text("Text switch")
    override val imageSwitchTitle = text("Image switch")
    override val textImageSwitchTitle = text("Text image switch")
    override val textPairSwitchTitle = text("Text pair switch")

    override val emptyToggle = toggle()
    override val textToggle = toggleWithText("Label")
    override val imageToggle = toggleWithImage(SampleImageResource.ICON_CLOSE)
    override val textImageToggle = toggleWithTextImage("Label", SampleImageResource.ICON_CLOSE)
    override val textPairToggle = toggleWithTextPair("Label", "Subtitle")
}
