package com.mirego.trikot.kword

import com.mirego.trikot.kword.internal.PlatformTranslationLoader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import okio.BufferedSource
import okio.FileNotFoundException
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

object KwordLoader {
    fun setCurrentLanguageCode(code: String, basePaths: List<String>, fileSystem: FileSystem?, cacheDirPath: String?) {
        setCurrentLanguageCodes(KWord, basePaths, fileSystem, cacheDirPath, code)
    }

    fun setCurrentLanguageCode(i18N: I18N, basePaths: List<String>, fileSystem: FileSystem?, cacheDirPath: String?, code: String) {
        setCurrentLanguageCodes(i18N, basePaths, fileSystem, cacheDirPath, code)
    }

    fun setCurrentLanguageCodes(i18N: I18N, basePaths: List<String>, fileSystem: FileSystem?, cacheDirPath: String?, vararg codes: String) {
        val map = mutableMapOf<String, String>()
        val cachedMap = mutableMapOf<String, String>()
        basePaths.forEach { basePath ->
            val variant = mutableListOf<String>()
            val variantCombinations = mutableListOf<String>()
            codes.forEach { code ->
                variant.add(code)
                val variantPath = variant.joinToString(".")
                variantCombinations.add(variantPath)
                val filePath = "$basePath.$variantPath.json"
                PlatformTranslationLoader.loadTranslations(filePath)
                    ?.let { map.putAll(it) }
                    ?: run { println("path not found $filePath") }
            }
            fetchCachedTranslationFiles(fileSystem, cacheDirPath, cachedMap, variantCombinations.toList())
            fetchRemoteTranslationFiles(i18N, fileSystem, cacheDirPath, map, variantCombinations.toList())
        }
        map.putAll(cachedMap)
        i18N.changeLocaleStrings(map)
    }

    private fun fetchCachedTranslationFiles(fileSystem: FileSystem?, basePath: String?, map: MutableMap<String, String>, languageCodes: List<String>) {
        fileSystem?.let { fileSystemVal ->
            basePath?.let { basePathVal ->
                val okioPath = "$basePathVal/translations".toPath()
                languageCodes.forEach { languageCode ->
                    try {
                        val destinationPath = okioPath / "translation.$languageCode.json"
                        val source: BufferedSource = fileSystemVal.source(destinationPath).buffer()
                        val result = source.readUtf8()
                        val fetchedMap = Json.decodeFromString<Map<String, String>>(result)
                        map.putAll(fetchedMap)
                    } catch (e: FileNotFoundException) {
                        println("@@ File not found: $e")
                    }
                }
            }
        }
    }

    private fun fetchRemoteTranslationFiles(i18N: I18N, fileSystem: FileSystem?, basePath: String?, map: MutableMap<String, String>, languageCodes: List<String>) {
        CoroutineScope(Dispatchers.Main).launch {
            val client = HttpClient()
            coroutineScope {
                languageCodes.map { variantCombination ->
                    async {
                        try {
                            client.get("https://fdca-70-28-93-175.ngrok-free.app/export") {
                                parameter("language", variantCombination)
                                parameter("project_id", "915cd661-ef3a-4b89-ac60-fb82ef90a61a")
//                          parameter("document_path", "core")
                                parameter("document_format", "simple_json")
                            }.body<String>().let { jsonStringified ->
                                Json.decodeFromString<Map<String, String>>(jsonStringified)
                            }.also {
                                fileSystem?.let { fileSystemVal ->
                                    basePath?.let { basePathVal ->
                                        saveTranslations(variantCombination, it, fileSystemVal, basePathVal)
                                    }
                                }
                            }.let {
                                Result.success(it)
                            }
                        } catch (e: ClientRequestException) {
                            println("@@ Request failed for $variantCombination: $e")
                            Result.failure(e)
                        } catch (e: Exception) {
                            println("@@ General error occurred: $e")
                            Result.failure(e)
                        }
                    }
                }.awaitAll().forEach { result ->
                    result.fold(
                        onSuccess = {
                            println("@@ Add map to map")
                            map.putAll(it)
                        },
                        onFailure = {}
                    )
                }.also {
                    println("@@ Add map to i18n")
                    i18N.changeLocaleStrings(map)
                }
                client.close()
            }
        }
    }

    private fun saveTranslations(languageCode: String, map: Map<String, String>, fileSystem: FileSystem, basePath: String) {
        val jsonMap = encodeTranslationFile(map)
        val okioPath = "$basePath/translations".toPath()
        if (!fileSystem.exists(okioPath)) {
            try {
                fileSystem.createDirectory(okioPath, true)
            } catch (e: Exception) {
                println("@@@ CANNOT CREATE DIR $e")
            }
        }

        val destinationPath = okioPath / "translation.$languageCode.json"
        fileSystem.write(destinationPath) {
            writeUtf8(jsonMap)
        }
    }

    private fun encodeTranslationFile(jsonContent: Map<String, String>): String {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            allowSpecialFloatingPointValues = true
        }

        return json.encodeToString(MapSerializer(String.serializer(), String.serializer()), jsonContent)
    }
}