package com.mirego.trikot.viewmodels.declarative.extensions

import androidx.compose.ui.text.input.ImeAction
import com.mirego.trikot.viewmodels.declarative.properties.KeyboardReturnKeyType
import androidx.compose.ui.text.input.KeyboardType as ComposeKeyboardType
import com.mirego.trikot.viewmodels.declarative.properties.KeyboardType as TrikotKeyboardType

val TrikotKeyboardType.composeValue: ComposeKeyboardType
    get() = when (this) {
        TrikotKeyboardType.Default -> ComposeKeyboardType.Text
        TrikotKeyboardType.Ascii -> ComposeKeyboardType.Ascii
        TrikotKeyboardType.Number -> ComposeKeyboardType.Number
        TrikotKeyboardType.Email -> ComposeKeyboardType.Email
        TrikotKeyboardType.Password -> ComposeKeyboardType.Password
        TrikotKeyboardType.NumberPassword -> ComposeKeyboardType.NumberPassword
        TrikotKeyboardType.Phone -> ComposeKeyboardType.Phone
        TrikotKeyboardType.URL -> ComposeKeyboardType.Uri
    }

val KeyboardReturnKeyType.composeValue: ImeAction
    get() = when (this) {
        KeyboardReturnKeyType.Default -> ImeAction.Default
        KeyboardReturnKeyType.Done -> ImeAction.Done
        KeyboardReturnKeyType.Go -> ImeAction.Go
        KeyboardReturnKeyType.Next -> ImeAction.Next
        KeyboardReturnKeyType.Search -> ImeAction.Search
        KeyboardReturnKeyType.Send -> ImeAction.Send
    }
