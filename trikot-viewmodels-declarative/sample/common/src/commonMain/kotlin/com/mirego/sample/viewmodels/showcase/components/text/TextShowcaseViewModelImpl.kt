package com.mirego.sample.viewmodels.showcase.components.text

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents

class TextShowcaseViewModelImpl(i18N: I18N, cancellableManager: CancellableManager) :
    ShowcaseViewModelImpl(cancellableManager), TextShowcaseViewModel {
    override val title = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TITLE],
        cancellableManager
    )

    override val largeTitle = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_LARGE_TITLE],
        cancellableManager
    )

    override val title1 = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE1],
        cancellableManager
    )

    override val title1Bold = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE1_BOLD],
        cancellableManager
    )

    override val title2 = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE2],
        cancellableManager
    )

    override val title2Bold = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE2_BOLD],
        cancellableManager
    )

    override val title3 = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE3],
        cancellableManager
    )

    override val headline = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_HEADLINE],
        cancellableManager
    )

    override val body = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_BODY],
        cancellableManager
    )

    override val bodyMedium = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_BODY_MEDIUM],
        cancellableManager
    )

    override val button = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_BUTTON],
        cancellableManager
    )

    override val callout = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_CALLOUT],
        cancellableManager
    )

    override val subheadline = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_SUBHEADLINE],
        cancellableManager
    )

    override val footnote = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_FOOTNOTE],
        cancellableManager
    )

    override val caption1 = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_CAPTION1],
        cancellableManager
    )

    override val caption2 = VMDComponents.Text.withContent(
        i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_CAPTION2],
        cancellableManager
    )
}
