package com.mirego.trikot.kword.remote.update.internal

import com.mirego.trikot.kword.I18N
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.IOException
import okio.Path.Companion.toPath
import okio.buffer

internal class InternalCacheWrapper(
    private val internalStoragePath: String?,
    private val translationsVersion: String?,
    private val fileSystem: FileSystem?
) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        allowSpecialFloatingPointValues = true
    }

    fun loadTranslationsFromCache(i18N: I18N, baseFileName: String, baseMap: MutableMap<String, String>, languageCodes: List<String>) {
        fileSystem?.let { fileSystemVal ->
            internalStoragePath?.let { internalStoragePathVal ->
                translationsVersion?.let { translationsVersionVal ->
                    if (internalStoragePathVal.isEmpty() || translationsVersionVal.isEmpty() || baseFileName.isEmpty()) {
                        return
                    }

                    val baseDirectoryPath = "$internalStoragePathVal/translations/$translationsVersionVal".toPath()
                    languageCodes.forEach { languageCode ->
                        try {
                            val fullSourcePath = baseDirectoryPath / buildFileName(baseFileName, languageCode)
                            val source = fileSystemVal.source(fullSourcePath).buffer()
                            val result = source.readUtf8()
                            val loadedMap = json.decodeFromString<Map<String, String>>(result)
                            baseMap.putAll(loadedMap)
                        } catch (e: Exception) {
                            println(e)
                        }
                    }

                    if (baseMap.isNotEmpty()) {
                        i18N.changeLocaleStrings(baseMap)
                    }
                }
            }
        }
    }

    fun saveTranslationsToCache(baseFileName: String, baseMap: Map<String, String>, languageCode: String) {
        fileSystem?.let { fileSystemVal ->
            internalStoragePath?.let { internalStoragePathVal ->
                translationsVersion?.let { translationsVersionVal ->
                    if (internalStoragePathVal.isEmpty() || translationsVersionVal.isEmpty() || baseFileName.isEmpty()) {
                        return
                    }

                    val jsonMap = json.encodeToString(MapSerializer(String.serializer(), String.serializer()), baseMap)
                    var baseDirectoryPath = "$internalStoragePathVal/translations".toPath()

                    try {
                        if (!fileSystemVal.exists(baseDirectoryPath)) {
                            fileSystemVal.createDirectory(baseDirectoryPath, true)
                        }

                        baseDirectoryPath /= translationsVersionVal
                        if (!fileSystemVal.exists(baseDirectoryPath)) {
                            fileSystemVal.createDirectory(baseDirectoryPath, true)
                        }

                        val fullDestinationPath = baseDirectoryPath / buildFileName(baseFileName, languageCode)
                        fileSystemVal.write(fullDestinationPath) {
                            writeUtf8(jsonMap)
                        }
                    } catch (e: IOException) {
                        println(e)
                    }
                }
            }
        }
    }

    private fun buildFileName(baseFileName: String, languageCode: String) = "$baseFileName.$languageCode.json"
}
