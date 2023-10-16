package com.mirego.sample.viewmodels.showcase.components.text

import com.mirego.sample.KWordTranslation
import com.mirego.sample.extensions.rangeOf
import com.mirego.sample.resources.SampleTextStyleResource
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.properties.VMDRichTextSpan
import com.mirego.trikot.viewmodels.declarative.properties.VMDSpanStyleResourceTransform
import com.mirego.trikot.viewmodels.declarative.viewmodel.htmlText
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope

class TextShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) :
    ShowcaseViewModelImpl(coroutineScope), TextShowcaseViewModel {

    private companion object {
        private const val HTML_TEXT = """
             <h1>Heading 1</h1>
             <p>Paragraph with <a href="https://mirego.com">Link</a></p>
             <h2>Heading 2</h2>
             <p>This is some html. Look, here's an <u>underline</u>.</p>
             <p>Look, this is <em>emphasized.</em> And here's some <b>bold</b>.</p>
             <p>Here are UL list items:
             <ul>
             <li>One</li>
             <li>Two</li>
             <li>Three</li>
             </ul>
             <p>Here are OL list items:
             <ol>
             <li>One</li>
             <li>Two</li>
             <li>Three</li>
             </ol>
        """
    }

    override val title = text(i18N[KWordTranslation.TEXT_SHOWCASE_TITLE])

    override val largeTitle = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_LARGE_TITLE]) {
        testIdentifier = "largeTitle"
    }

    override val title1 = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE1]) {
        testIdentifier = "title1"
    }

    override val title1Bold = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE1_BOLD])

    override val title2 = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE2])

    override val title2Bold = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE2_BOLD])

    override val title3 = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE3])

    override val headline = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_HEADLINE])

    override val body = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_BODY])

    override val bodyMedium = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_BODY_MEDIUM])

    override val button = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_BUTTON])

    override val callout = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_CALLOUT])

    override val subheadline = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_SUBHEADLINE])

    override val footnote = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_FOOTNOTE])

    override val caption1 = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_CAPTION1])

    override val caption2 = text(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_CAPTION2])

    override val richText: VMDTextViewModel = text(
        content = i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_RICH_TEXT],
        spans = listOf(
            VMDRichTextSpan(
                range = i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_RICH_TEXT].rangeOf(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_RICH_TEXT_HIGHLIGHT]),
                transform = VMDSpanStyleResourceTransform(SampleTextStyleResource.HIGHLIGHTED)
            )
        )
    )

    override val htmlText = htmlText(HTML_TEXT)

    override val htmlTextWithLinks = htmlText(HTML_TEXT)
}
