package com.mirego.trikot.foundation.strings

import java.text.Normalizer
import java.util.regex.Pattern

actual fun String.normalize(): String =
    Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace(NativeStringExtensions.diacriticsRegex, "")

private object NativeStringExtensions {
    val diacriticsRegex: Regex by lazy {
        Pattern.compile("\\p{InCombiningDiacriticalMarks}+").toRegex()
    }
}
