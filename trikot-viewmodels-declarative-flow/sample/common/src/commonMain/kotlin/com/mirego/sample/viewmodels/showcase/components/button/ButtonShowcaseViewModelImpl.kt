package com.mirego.sample.viewmodels.showcase.components.button

import com.mirego.sample.KWordTranslation
import com.mirego.sample.resources.SampleImageResource
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithText
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithTextImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithTextPair
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope

class ButtonShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), ButtonShowcaseViewModel {
    override val title = text(i18N[KWordTranslation.BUTTON_SHOWCASE_TITLE])
    override val textButtonTitle = text(i18N[KWordTranslation.BUTTON_SHOWCASE_TEXT_TITLE])
    override val imageButtonTitle = text(i18N[KWordTranslation.BUTTON_SHOWCASE_IMAGE_TITLE])
    override val textImageButtonTitle = text(i18N[KWordTranslation.BUTTON_SHOWCASE_TEXT_IMAGE_TITLE])
    override val textPairButtonTitle = text(i18N[KWordTranslation.BUTTON_SHOWCASE_TEXT_PAIR_TITLE])
    override val textButton = buttonWithText(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL])
    override val imageButton = buttonWithImage(SampleImageResource.ICON_CLOSE)
    override val textImageButton = buttonWithTextImage(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL], SampleImageResource.ICON_CLOSE)
    override val textPairButton = buttonWithTextPair(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL], i18N[KWordTranslation.BUTTON_SHOWCASE_SUBTITLE])
}
