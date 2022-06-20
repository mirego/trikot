package com.mirego.trikot.viewmodels.declarative.properties

/**
 * The automatic capitalization behavior of a text-based view.
 * Applies to languages which has upper-case and lower-case letters.
 *
 * [SwiftUI](https://developer.apple.com/documentation/uikit/uitextautocapitalizationtype)
 * [Compose](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/input/KeyboardCapitalization.html)
 */
enum class VMDKeyboardAutoCapitalization {
    None,
    Sentences,
    Words,
    Characters
}
