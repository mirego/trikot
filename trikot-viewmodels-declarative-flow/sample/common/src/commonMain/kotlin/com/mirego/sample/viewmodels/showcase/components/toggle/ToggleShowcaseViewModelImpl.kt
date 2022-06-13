package com.mirego.sample.viewmodels.showcase.components.toggle

import com.mirego.sample.KWordTranslation
import com.mirego.sample.resources.SampleImageResource
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDToggleViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.content.VMDNoContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent
import kotlinx.coroutines.CoroutineScope

class ToggleShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), ToggleShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TOGGLE_SHOWCASE_TITLE], coroutineScope)

    override val checkboxTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TOGGLE_SHOWCASE_CHECKBOX_TITLE], coroutineScope)
    override val textCheckboxTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_CHECKBOX_TITLE], coroutineScope)
    override val imageCheckboxTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TOGGLE_SHOWCASE_IMAGE_CHECKBOX_TITLE], coroutineScope)
    override val textImageCheckboxTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_IMAGE_CHECKBOX_TITLE], coroutineScope)
    override val textPairCheckboxTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_PAIR_CHECKBOX_TITLE], coroutineScope)

    override val switchTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TOGGLE_SHOWCASE_SWITCH_TITLE], coroutineScope)
    override val textSwitchTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_SWITCH_TITLE], coroutineScope)
    override val imageSwitchTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TOGGLE_SHOWCASE_IMAGE_SWITCH_TITLE], coroutineScope)
    override val textImageSwitchTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_IMAGE_SWITCH_TITLE], coroutineScope)
    override val textPairSwitchTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.TOGGLE_SHOWCASE_TEXT_PAIR_SWITCH_TITLE], coroutineScope)

    override val emptyToggle: VMDToggleViewModel<VMDNoContent> = VMDComponents.Toggle.empty(coroutineScope)
    override val textToggle: VMDToggleViewModel<VMDTextContent> = VMDComponents.Toggle.withText(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL], false, coroutineScope)
    override val imageToggle: VMDToggleViewModel<VMDImageContent> = VMDComponents.Toggle.withImage(SampleImageResource.ICON_CLOSE, false, coroutineScope)
    override val textImageToggle: VMDToggleViewModel<VMDTextImagePairContent> = VMDComponents.Toggle.withTextImage(
        VMDTextImagePairContent(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL], SampleImageResource.ICON_CLOSE),
        false,
        coroutineScope
    )
    override val textPairToggle: VMDToggleViewModel<VMDTextPairContent> = VMDComponents.Toggle.withTextPair(
        VMDTextPairContent(i18N[KWordTranslation.BUTTON_SHOWCASE_LABEL], i18N[KWordTranslation.BUTTON_SHOWCASE_SUBTITLE]),
        false,
        coroutineScope
    )
}
