package com.mirego.trikot.viewmodels.declarative.compose.extensions

import androidx.compose.ui.text.input.ImeAction
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardReturnKeyType
import androidx.compose.ui.text.input.KeyboardType as ComposeKeyboardType
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardType as TrikotKeyboardType

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

val VMDKeyboardReturnKeyType.composeValue: ImeAction
    get() = when (this) {
        VMDKeyboardReturnKeyType.Default -> ImeAction.Default
        VMDKeyboardReturnKeyType.Done -> ImeAction.Done
        VMDKeyboardReturnKeyType.Go -> ImeAction.Go
        VMDKeyboardReturnKeyType.Next -> ImeAction.Next
        VMDKeyboardReturnKeyType.Search -> ImeAction.Search
        VMDKeyboardReturnKeyType.Send -> ImeAction.Send
    }
