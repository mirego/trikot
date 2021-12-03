package com.mirego.sample.viewmodels.showcase.text

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents

class TextShowcaseViewModelImpl(i18N: I18N, cancellableManager: CancellableManager) : ShowcaseViewModelImpl(cancellableManager), TextShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TEXT_SHOWCASE_TITLE], cancellableManager)
    override val title1: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE1], cancellableManager)
    override val title1Bold: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE1_BOLD], cancellableManager)
    override val title2: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE2], cancellableManager)
    override val title2Bold: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_TITLE2_BOLD], cancellableManager)
    override val body: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_BODY], cancellableManager)
    override val bodyMedium: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_BODY_MEDIUM], cancellableManager)
}
