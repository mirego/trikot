package com.mirego.sample.viewmodels.showcase.components.toggle

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class ToggleShowcaseViewModelPreview : VMDViewModelImpl(CancellableManager()), ToggleShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Text Showcase", cancellableManager)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, cancellableManager)

    override val checkboxTitle = VMDComponents.Text.withContent("Checkbox", cancellableManager)
    override val textCheckboxTitle = VMDComponents.Text.withContent("Text checkbox", cancellableManager)
    override val imageCheckboxTitle = VMDComponents.Text.withContent("Image checkbox", cancellableManager)
    override val textImageCheckboxTitle = VMDComponents.Text.withContent("Text image checkbox", cancellableManager)
    override val textPairCheckboxTitle = VMDComponents.Text.withContent("Text pair checkbox", cancellableManager)
    override val switchTitle = VMDComponents.Text.withContent("Switch", cancellableManager)
    override val textSwitchTitle = VMDComponents.Text.withContent("Text switch", cancellableManager)
    override val imageSwitchTitle = VMDComponents.Text.withContent("Image switch", cancellableManager)
    override val textImageSwitchTitle = VMDComponents.Text.withContent("Text image switch", cancellableManager)
    override val textPairSwitchTitle = VMDComponents.Text.withContent("Text pair switch", cancellableManager)

    override val emptyToggle = VMDComponents.Toggle.empty(cancellableManager)
    override val textToggle = VMDComponents.Toggle.withText("Label", false, cancellableManager)
    override val imageToggle = VMDComponents.Toggle.withImage(SampleImageResource.ICON_CLOSE, false, cancellableManager)
    override val textImageToggle = VMDComponents.Toggle.withTextImage(VMDTextImagePairContent("Label", SampleImageResource.ICON_CLOSE), false, cancellableManager)
    override val textPairToggle = VMDComponents.Toggle.withTextPair(VMDTextPairContent("Label", "Subtitle"), false, cancellableManager)
}
