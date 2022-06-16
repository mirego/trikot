package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardAutoCapitalization
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardReturnKeyType
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardType
import com.mirego.trikot.viewmodels.declarative.properties.VMDTextContentType

interface VMDTextFieldViewModel : VMDControlViewModel {
    val text: String
    val placeholder: String
    val keyboardType: VMDKeyboardType
    val keyboardReturnKeyType: VMDKeyboardReturnKeyType
    val contentType: VMDTextContentType?
    val autoCorrect: Boolean
    val autoCapitalization: VMDKeyboardAutoCapitalization
    val onReturnKeyTap: () -> Unit

    val formatText: (text: String) -> String
    val unformatText: (text: String) -> String

    fun onValueChange(text: String)
}
