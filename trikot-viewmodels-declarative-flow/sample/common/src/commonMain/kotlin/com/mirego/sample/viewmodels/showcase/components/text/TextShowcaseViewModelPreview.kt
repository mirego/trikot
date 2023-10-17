package com.mirego.sample.viewmodels.showcase.components.text

import com.mirego.sample.extensions.rangeOf
import com.mirego.sample.resources.SampleImageResource
import com.mirego.sample.resources.SampleTextStyleResource
import com.mirego.trikot.viewmodels.declarative.components.VMDHtmlTextViewModel
import com.mirego.trikot.viewmodels.declarative.properties.VMDRichTextSpan
import com.mirego.trikot.viewmodels.declarative.properties.VMDSpanStyleResourceTransform
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.htmlText
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

    private val richTextContent = "Rich text demo"
    private val highlightedTextContent = "text"

    override val richText = text(
        content = richTextContent,
        spans = listOf(
            VMDRichTextSpan(
                range = richTextContent.rangeOf(highlightedTextContent),
                transform = VMDSpanStyleResourceTransform(SampleTextStyleResource.HIGHLIGHTED)
            )
        )
    )

    override val htmlText: VMDHtmlTextViewModel = htmlText("<em>html text</em>")

    override val htmlTextWithLinks: VMDHtmlTextViewModel = htmlText("<a href=\"https://mirego.com\">html link</a>")
}
