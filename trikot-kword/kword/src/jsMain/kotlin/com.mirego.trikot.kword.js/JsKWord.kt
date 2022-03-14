package com.mirego.trikot.kword.js

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KwordLoader

object JsKWord {
    private val DEFAULT_PATHS = listOf(
        "translation"
    )

    fun setCurrentLanguageCode(code: String) {
        KwordLoader.setCurrentLanguageCode(code, DEFAULT_PATHS)
    }

    fun setCurrentLanguageCode(i18N: I18N, code: String) {
        KwordLoader.setCurrentLanguageCodes(i18N, DEFAULT_PATHS, code)
    }

    fun setCurrentLanguageCodes(i18N: I18N, vararg codes: String) {
        KwordLoader.setCurrentLanguageCodes(i18N, DEFAULT_PATHS, *codes)
    }
}
