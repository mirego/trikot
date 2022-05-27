package com.mirego.sample

import com.mirego.trikot.kword.KWordKey
import kotlin.String

enum class KWordTranslation(override val translationKey: String) : KWordKey {
    TEXT("text"),

    BUTTON_CHANGE_LANGUAGE("button_change_language");
}
