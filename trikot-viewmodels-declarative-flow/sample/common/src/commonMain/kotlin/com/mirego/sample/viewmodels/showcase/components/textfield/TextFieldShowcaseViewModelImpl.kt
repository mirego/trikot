package com.mirego.sample.viewmodels.showcase.components.textfield

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDTextFieldViewModel
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardAutoCapitalization
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardReturnKeyType
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map

class TextFieldShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), TextFieldShowcaseViewModel {
    override val title = VMDComponents.Text.withContent(i18N[KWordTranslation.TEXT_FIELD_SHOWCASE_TITLE], coroutineScope)

    override val textField = VMDComponents.TextField.empty(coroutineScope) {
        placeholder = i18N[KWordTranslation.TEXT_FIELD_SHOWCASE_TEXT_FIELD_PLACEHOLDER]
        keyboardType = VMDKeyboardType.Ascii
        keyboardReturnKeyType = VMDKeyboardReturnKeyType.Default
        autoCorrect = false
        autoCapitalization = VMDKeyboardAutoCapitalization.Sentences
    }

    override val characterCountText = VMDComponents.Text.empty(coroutineScope) {
        bindText(
            textField.flowForProperty(VMDTextFieldViewModel::text).map {
                i18N.t(KWordTranslation.TEXT_FIELD_SHOWCASE_CHARACTER_COUNT, it.length, "count" to "${it.length}")
            }
        )
    }

    override val clearButton = VMDComponents.Button.withText(i18N[KWordTranslation.TEXT_FIELD_SHOWCASE_CLEAR_BUTTON], coroutineScope) {
        setAction {
            textField.text = ""
        }
    }
}
