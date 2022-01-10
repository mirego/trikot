package com.mirego.trikot.viewmodels.declarative.properties

/**
 * Constants that specify the text displayed in the return key of a keyboard.
 *
 * [SwiftUI](https://developer.apple.com/documentation/uikit/uireturnkeytype)
 * [Compose](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/input/ImeAction.html)
 */
enum class VMDKeyboardReturnKeyType {
    Default,
    Done,
    Go,
    Next,
    Search,
    Send
}
