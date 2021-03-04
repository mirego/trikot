package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.properties.KeyboardAutoCapitalization
import com.mirego.trikot.viewmodels.declarative.properties.KeyboardReturnKeyType
import com.mirego.trikot.viewmodels.declarative.properties.KeyboardType
import com.mirego.trikot.viewmodels.declarative.properties.TextContentType

interface TextFieldViewModel : ControlViewModel {
    val text: String
    val placeholder: String
    val keyboardType: KeyboardType
    val keyboardReturnKeyType: KeyboardReturnKeyType
    val contentType: TextContentType?
    val autoCorrect: Boolean
    val autoCapitalization: KeyboardAutoCapitalization
    val onReturnKeyTap: () -> Unit

    fun onValueChange(text: String)
}
