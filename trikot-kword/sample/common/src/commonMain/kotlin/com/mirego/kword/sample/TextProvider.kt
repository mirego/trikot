package com.mirego.kword.sample

import com.mirego.sample.KWordTranslation
import com.mirego.trikot.kword.flow.FlowI18N

class TextProvider(private val i18N: FlowI18N) {
    val text = i18N[KWordTranslation.TEXT].wrap()
    val buttonText = i18N[KWordTranslation.BUTTON_CHANGE_LANGUAGE].wrap()

    fun toggleLanguage() {
        if (i18N.language.value == "en") {
            i18N.changeLanguage("fr")
        } else {
            i18N.changeLanguage("en")
        }
    }
}
