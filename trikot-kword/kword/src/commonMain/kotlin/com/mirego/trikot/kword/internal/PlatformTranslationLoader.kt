package com.mirego.trikot.kword.internal

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal expect object PlatformTranslationLoader {
    fun loadTranslations(path: String): Map<String, String>?
}

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    allowSpecialFloatingPointValues = true
}

internal fun decodeTranslationFile(jsonContent: String) =
    json.decodeFromString<Map<String, String>>(jsonContent)
