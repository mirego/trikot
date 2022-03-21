package com.mirego.trikot.kword

import com.mirego.trikot.kword.internal.PlatformTranslationLoader

object KwordLoader {
    fun setCurrentLanguageCode(code: String, basePaths: List<String>) {
        setCurrentLanguageCodes(KWord, basePaths, code)
    }

    fun setCurrentLanguageCode(i18N: I18N, basePaths: List<String>, code: String) {
        setCurrentLanguageCodes(i18N, basePaths, code)
    }

    fun setCurrentLanguageCodes(i18N: I18N, basePaths: List<String>, vararg codes: String) {
        val map = mutableMapOf<String, String>()
        basePaths.forEach { basePath ->
            val variant = mutableListOf<String>()
            codes.forEach { code ->
                variant.add(code)
                val variantPath = variant.joinToString(".")
                val filePath = "$basePath.$variantPath.json"
                PlatformTranslationLoader.loadTranslations(filePath)
                    ?.let { map.putAll(it) }
                    ?: run { println("path not found $filePath") }
            }
        }
        i18N.changeLocaleStrings(map)
    }
}
