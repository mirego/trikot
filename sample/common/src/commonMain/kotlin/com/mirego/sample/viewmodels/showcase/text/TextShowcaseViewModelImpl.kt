package com.mirego.sample.viewmodels.showcase.text

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponentsFactory

class TextShowcaseViewModelImpl(i18N: I18N, cancellableManager: CancellableManager) : ShowcaseViewModelImpl(cancellableManager), TextShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.TEXT_SHOWCASE_TITLE], cancellableManager)
    override val text: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.TEXT_SHOWCASE_TEXT_NORMAL], cancellableManager)
}
