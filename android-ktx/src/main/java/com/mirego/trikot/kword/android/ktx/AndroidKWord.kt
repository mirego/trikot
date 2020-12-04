package com.mirego.trikot.kword.android.ktx

import android.util.Log
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KWord
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException

object AndroidKWord {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        allowSpecialFloatingPointValues = true
    }

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
                val fileContent =
                    KWord::class.java.getResource("/translations/translation.$variantPath.json")!!
                        .readText()
                map.putAll(json.decodeFromString<Map<String, String>>(fileContent))
            } catch (ioException: IOException) {
                Log.v("Kword", "Unable to load translation $variantPath", ioException)
            }
        }
        i18N.changeLocaleStrings(map)
    }
}
