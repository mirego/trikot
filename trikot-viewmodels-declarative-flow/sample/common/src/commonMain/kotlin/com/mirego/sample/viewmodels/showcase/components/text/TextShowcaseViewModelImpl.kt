package com.mirego.sample.viewmodels.showcase.components.text

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import kotlinx.coroutines.CoroutineScope

class TextShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) :
    ShowcaseViewModelImpl(coroutineScope), TextShowcaseViewModel {
    override val title = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TITLE],
        coroutineScope
    )

    override val largeTitle = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_LARGE_TITLE],
        coroutineScope
    )

    override val title1 = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE1],
        coroutineScope
    )

    override val title1Bold = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE1_BOLD],
        coroutineScope
    )

    override val title2 = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE2],
        coroutineScope
    )

    override val title2Bold = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE2_BOLD],
        coroutineScope
    )

    override val title3 = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE3],
        coroutineScope
    )

    override val headline = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_HEADLINE],
        coroutineScope
    )

    override val body = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_BODY],
        coroutineScope
    )

    override val bodyMedium = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_BODY_MEDIUM],
        coroutineScope
    )

    override val button = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_BUTTON],
        coroutineScope
    )

    override val callout = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_CALLOUT],
        coroutineScope
    )

    override val subheadline = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_SUBHEADLINE],
        coroutineScope
    )

    override val footnote = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_FOOTNOTE],
        coroutineScope
    )

    override val caption1 = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_CAPTION1],
        coroutineScope
    )

    override val caption2 = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_CAPTION2],
        coroutineScope
    )
}
