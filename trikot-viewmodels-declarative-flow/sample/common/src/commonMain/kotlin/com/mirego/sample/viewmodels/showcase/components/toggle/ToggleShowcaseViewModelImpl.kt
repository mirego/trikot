package com.mirego.sample.viewmodels.showcase.components.toggle

import com.mirego.sample.KWordTranslation
import com.mirego.sample.resources.SampleImageResource
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDToggleViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.content.VMDNoContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import com.mirego.trikot.viewmodels.declarative.viewmodel.toggle
import com.mirego.trikot.viewmodels.declarative.viewmodel.toggleWithImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.toggleWithText
import com.mirego.trikot.viewmodels.declarative.viewmodel.toggleWithTextImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.toggleWithTextPair
import kotlinx.coroutines.CoroutineScope

class ToggleShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), ToggleShowcaseViewModel {
    override val title: VMDTextViewModel = text(i18N[KWordTranslation.TOGGLE_SHOWCASE_TITLE])

    override val checkboxTitle: VMDTextViewModel = text(i18N[KWordTranslation.TOGGLE_SHOWCASE_CHECKBOX_TITLE])
    override val textCheckboxTitle: VMDTextViewModel = text(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_CHECKBOX_TITLE])
    override val imageCheckboxTitle: VMDTextViewModel = text(i18N[KWordTranslation.TOGGLE_SHOWCASE_IMAGE_CHECKBOX_TITLE])
    override val textImageCheckboxTitle: VMDTextViewModel = text(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_IMAGE_CHECKBOX_TITLE])
    override val textPairCheckboxTitle: VMDTextViewModel = text(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_PAIR_CHECKBOX_TITLE])

    override val switchTitle: VMDTextViewModel = text(i18N[KWordTranslation.TOGGLE_SHOWCASE_SWITCH_TITLE])
    override val textSwitchTitle: VMDTextViewModel = text(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_SWITCH_TITLE])
    override val imageSwitchTitle: VMDTextViewModel = text(i18N[KWordTranslation.TOGGLE_SHOWCASE_IMAGE_SWITCH_TITLE])
    override val textImageSwitchTitle: VMDTextViewModel = text(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_IMAGE_SWITCH_TITLE])
    override val textPairSwitchTitle: VMDTextViewModel = text(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_PAIR_SWITCH_TITLE])

    override val emptyToggle: VMDToggleViewModel<VMDNoContent> = toggle()
    override val textToggle: VMDToggleViewModel<VMDTextContent> = toggleWithText(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL])
    override val imageToggle: VMDToggleViewModel<VMDImageContent> = toggleWithImage(SampleImageResource.ICON_CLOSE)
    override val textImageToggle: VMDToggleViewModel<VMDTextImagePairContent> = toggleWithTextImage(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL], SampleImageResource.ICON_CLOSE)
    override val textPairToggle: VMDToggleViewModel<VMDTextPairContent> = toggleWithTextPair(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL], i18N[KWordTranslation.BUTTON_SHOWCASE_SUBTITLE])
}
