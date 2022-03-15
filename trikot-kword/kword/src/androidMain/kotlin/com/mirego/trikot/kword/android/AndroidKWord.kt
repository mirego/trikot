package com.mirego.trikot.kword.android

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KwordLoader

object AndroidKWord {
    private val DEFAULT_PATHS = listOf(
        "/translations/translation"
    )

    fun setCurrentLanguageCode(code: String) {
        KwordLoader.setCurrentLanguageCode(code, DEFAULT_PATHS)
    }

    fun setCurrentLanguageCode(i18N: I18N, code: String) {
        KwordLoader.setCurrentLanguageCode(i18N, DEFAULT_PATHS, code)
    }

    fun setCurrentLanguageCodes(i18N: I18N, vararg codes: String) {
        KwordLoader.setCurrentLanguageCodes(i18N, DEFAULT_PATHS, *codes)
    }
}
