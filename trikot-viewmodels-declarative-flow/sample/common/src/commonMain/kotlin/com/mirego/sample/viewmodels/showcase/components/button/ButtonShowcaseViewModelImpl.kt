package com.mirego.sample.viewmodels.showcase.components.button

import com.mirego.sample.KWordTranslation
import com.mirego.sample.resources.SampleImageResource
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent
import kotlinx.coroutines.CoroutineScope

class ButtonShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), ButtonShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.BUTTON_SHOWCASE_TITLE], coroutineScope)
    override val textButtonTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.BUTTON_SHOWCASE_TEXT_TITLE], coroutineScope)
    override val imageButtonTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.BUTTON_SHOWCASE_IMAGE_TITLE], coroutineScope)
    override val textImageButtonTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.BUTTON_SHOWCASE_TEXT_IMAGE_TITLE], coroutineScope)
    override val textPairButtonTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.BUTTON_SHOWCASE_TEXT_PAIR_TITLE], coroutineScope)
    override val textButton: VMDButtonViewModelImpl<VMDTextContent> = VMDComponents.Button.withText(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL], coroutineScope)
    override val imageButton: VMDButtonViewModelImpl<VMDImageContent> = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, coroutineScope)
    override val textImageButton: VMDButtonViewModelImpl<VMDTextImagePairContent> = VMDComponents.Button.withTextImage(
        VMDTextImagePairContent(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL], SampleImageResource.ICON_CLOSE),
        coroutineScope
    )
    override val textPairButton: VMDButtonViewModelImpl<VMDTextPairContent> = VMDComponents.Button.withTextPair(
        VMDTextPairContent(
            i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL],
            i18N[KWordTranslation.BUTTON_SHOWCASE_SUBTITLE]
        ),
        coroutineScope
    )
}
