package com.mirego.sample.viewmodels.showcase.button

import com.mirego.sample.KWordTranslation
import com.mirego.sample.resources.SampleImageResource
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponentsFactory
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor

class ButtonShowcaseViewModelImpl(i18N: I18N, cancellableManager: CancellableManager) : ShowcaseViewModelImpl(cancellableManager), ButtonShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.BUTTON_SHOWCASE_TITLE], cancellableManager)
    override val textButtonTitle: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.BUTTON_SHOWCASE_TEXT_TITLE], cancellableManager)
    override val imageButtonTitle: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.BUTTON_SHOWCASE_IMAGE_TITLE], cancellableManager)
    override val textImageButtonTitle: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.BUTTON_SHOWCASE_TEXT_IMAGE_TITLE], cancellableManager)
    override val textPairButtonTitle: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.BUTTON_SHOWCASE_TEXT_PAIR_TITLE], cancellableManager)
    override val textButton: VMDButtonViewModelImpl<VMDTextContent> = VMDComponentsFactory.Companion.Button.withText(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL], cancellableManager)
    override val imageButton: VMDButtonViewModelImpl<VMDImageContent> = VMDComponentsFactory.Companion.Button.withImage(SampleImageResource.ICON_CLOSE, cancellableManager)
    override val textImageButton: VMDButtonViewModelImpl<VMDTextImagePairContent> = VMDComponentsFactory.Companion.Button.withTextImage(
        VMDTextImagePairContent(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL], VMDImageDescriptor.Local(SampleImageResource.ICON_CLOSE)),
        cancellableManager
    )
    override val textPairButton: VMDButtonViewModelImpl<VMDTextPairContent> = VMDComponentsFactory.Companion.Button.withTextPair(
        VMDTextPairContent(
            i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL],
            i18N[KWordTranslation.BUTTON_SHOWCASE_SUBTITLE]
        ),
        cancellableManager
    )
}
