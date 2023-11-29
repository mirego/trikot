package com.mirego.trikot.kword.internal

import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import okio.FileSystem

internal expect object PlatformTranslationSaver {
    fun saveTranslations(path: String, map: Map<String, String>, fileSystem: FileSystem?, cacheDirPath: String?)
}

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    allowSpecialFloatingPointValues = true
}

internal fun encodeTranslationFile(jsonContent: Map<String, String>) =
    json.encodeToString(MapSerializer(String.serializer(), String.serializer()), jsonContent)
