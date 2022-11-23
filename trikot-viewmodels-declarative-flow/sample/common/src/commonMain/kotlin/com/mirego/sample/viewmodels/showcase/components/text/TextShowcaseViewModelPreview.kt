package com.mirego.sample.viewmodels.showcase.components.text

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.MainScope

class TextShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), TextShowcaseViewModel {
    override val title = text("Text Showcase")

    override val closeButton = buttonWithImage(SampleImageResource.ICON_CLOSE)

    override val largeTitle = text("Large Title")

    override val title1 = text("Title 1")

    override val title1Bold = text("Title 1 Bold")

    override val title2 = text("Title 2")

    override val title2Bold = text("Title 2 Bold")

    override val title3 = text("Title 3")

    override val headline = text("Headline")

    override val body = text("Body")

    override val bodyMedium = text("Body Medium")

    override val button = text("Button")

    override val callout = text("Callout")

    override val subheadline = text("Subhealine")

    override val footnote = text("Footnote")

    override val caption1 = text("Caption 1")

    override val caption2 = text("Caption 2")
}
