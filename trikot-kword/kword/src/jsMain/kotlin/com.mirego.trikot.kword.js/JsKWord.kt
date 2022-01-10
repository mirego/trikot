package com.mirego.trikot.kword.js

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KWord
import kotlinext.js.require

object JsKWord {
    fun setCurrentLanguageCode(code: String) {
        setCurrentLanguageCodes(KWord, code)
    }

    fun setCurrentLanguageCode(i18N: I18N, code: String) {
        setCurrentLanguageCodes(i18N, code)
    }

    fun setCurrentLanguageCodes(i18N: I18N, vararg codes: String) {
        val map = mutableMapOf<String, String>()
        val variant = mutableListOf<String>()
        codes.forEach {
            variant.add(it)
            val variantPath = variant.joinToString(".")
            try {
                val translations = require("./translations/translation.$variantPath.json")
                map.putAll(mapOf(translations))
            } catch (error: dynamic) {
                println("Unable to parse JSON: $error")
            }
        }
        i18N.changeLocaleStrings(map)
    }

    private fun mapOf(jsObject: dynamic): Map<String, String> =
        entriesOf(jsObject).toMap()

    private fun entriesOf(jsObject: dynamic): List<Pair<String, String>> =
        (js("Object.entries") as (dynamic) -> Array<Array<String>>)
            .invoke(jsObject)
            .map { entry -> entry[0] to entry[1] }
}
