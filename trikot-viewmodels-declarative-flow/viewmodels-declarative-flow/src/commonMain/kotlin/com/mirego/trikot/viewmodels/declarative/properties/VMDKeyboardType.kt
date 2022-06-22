package com.mirego.trikot.viewmodels.declarative.properties

/**
 * Constants that specify the type of keyboard to display for a component that supports text as input.
 *
 * [SwiftUI](https://developer.apple.com/documentation/uikit/uikeyboardtype)
 * [Compose](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/input/KeyboardType)
 */
enum class VMDKeyboardType {
    Default,
    Ascii,
    Number,
    Email,
    Password,
    NumberPassword,
    Phone,
    URL
}
