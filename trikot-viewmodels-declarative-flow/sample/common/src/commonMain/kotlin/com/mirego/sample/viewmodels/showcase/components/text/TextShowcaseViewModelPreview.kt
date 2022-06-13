package com.mirego.sample.viewmodels.showcase.components.text

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.MainScope

class TextShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), TextShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Text Showcase", coroutineScope)

    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, coroutineScope)

    override val largeTitle = VMDComponents.Text.withContent(
        "Large Title",
        coroutineScope
    )

    override val title1 = VMDComponents.Text.withContent(
        "Title 1",
        coroutineScope
    )

    override val title1Bold = VMDComponents.Text.withContent(
        "Title 1 Bold",
        coroutineScope
    )

    override val title2 = VMDComponents.Text.withContent(
        "Title 2",
        coroutineScope
    )

    override val title2Bold = VMDComponents.Text.withContent(
        "Title 2 Bold",
        coroutineScope
    )

    override val title3 = VMDComponents.Text.withContent(
        "Title 3",
        coroutineScope
    )

    override val headline = VMDComponents.Text.withContent(
        "Headline",
        coroutineScope
    )

    override val body = VMDComponents.Text.withContent(
        "Body",
        coroutineScope
    )
    override val bodyMedium = VMDComponents.Text.withContent(
        "Body Medium",
        coroutineScope
    )

    override val button = VMDComponents.Text.withContent(
        "Button",
        coroutineScope
    )

    override val callout = VMDComponents.Text.withContent(
        "Callout",
        coroutineScope
    )

    override val subheadline = VMDComponents.Text.withContent(
        "Subhealine",
        coroutineScope
    )

    override val footnote = VMDComponents.Text.withContent(
        "Footnote",
        coroutineScope
    )

    override val caption1 = VMDComponents.Text.withContent(
        "Caption 1",
        coroutineScope
    )

    override val caption2 = VMDComponents.Text.withContent(
        "Caption 2",
        coroutineScope
    )
}
