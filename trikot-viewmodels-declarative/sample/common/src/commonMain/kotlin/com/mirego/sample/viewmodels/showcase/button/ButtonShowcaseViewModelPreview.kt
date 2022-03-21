package com.mirego.sample.viewmodels.showcase.button

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class ButtonShowcaseViewModelPreview : VMDViewModelImpl(CancellableManager()), ButtonShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Button Showcase", cancellableManager)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, cancellableManager)

    override val textButtonTitle = VMDComponents.Text.withContent("Text button", cancellableManager)
    override val textButton = VMDComponents.Button.withText("Label", cancellableManager)

    override val imageButtonTitle = VMDComponents.Text.withContent("Image button", cancellableManager)
    override val imageButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, cancellableManager)

    override val textImageButtonTitle = VMDComponents.Text.withContent("Text image button", cancellableManager)
    override val textImageButton = VMDComponents.Button.withTextImage(VMDTextImagePairContent("Label", SampleImageResource.ICON_CLOSE), cancellableManager)

    override val textPairButtonTitle = VMDComponents.Text.withContent("Text pair button", cancellableManager)
    override val textPairButton = VMDComponents.Button.withTextPair(VMDTextPairContent("Label", "Label"), cancellableManager)
}
