package com.mirego.sample.viewmodels.showcase.text

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class TextShowcaseViewModelPreview : VMDViewModelImpl(CancellableManager()), TextShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Text Showcase", cancellableManager)

    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, cancellableManager)

    override val largeTitle = VMDComponents.Text.withContent(
        "Large Title",
        cancellableManager
    )

    override val title1 = VMDComponents.Text.withContent(
        "Title 1",
        cancellableManager
    )

    override val title1Bold = VMDComponents.Text.withContent(
        "Title 1 Bold",
        cancellableManager
    )

    override val title2 = VMDComponents.Text.withContent(
        "Title 2",
        cancellableManager
    )

    override val title2Bold = VMDComponents.Text.withContent(
        "Title 2 Bold",
        cancellableManager
    )

    override val title3 = VMDComponents.Text.withContent(
        "Title 3",
        cancellableManager
    )

    override val headline = VMDComponents.Text.withContent(
        "Headline",
        cancellableManager
    )

    override val body = VMDComponents.Text.withContent(
        "Body",
        cancellableManager
    )
    override val bodyMedium = VMDComponents.Text.withContent(
        "Body Medium",
        cancellableManager
    )

    override val button = VMDComponents.Text.withContent(
        "Button",
        cancellableManager
    )

    override val callout = VMDComponents.Text.withContent(
        "Callout",
        cancellableManager
    )

    override val subheadline = VMDComponents.Text.withContent(
        "Subhealine",
        cancellableManager
    )

    override val footnote = VMDComponents.Text.withContent(
        "Footnote",
        cancellableManager
    )

    override val caption1 = VMDComponents.Text.withContent(
        "Caption 1",
        cancellableManager
    )

    override val caption2 = VMDComponents.Text.withContent(
        "Caption 2",
        cancellableManager
    )
}
