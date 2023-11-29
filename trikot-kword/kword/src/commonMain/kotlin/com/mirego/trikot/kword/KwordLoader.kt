package com.mirego.trikot.kword

import com.mirego.trikot.kword.internal.PlatformTranslationLoader
import com.mirego.trikot.kword.internal.encodeTranslationFile
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okio.BufferedSource
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
        CoroutineScope(Dispatchers.Main).launch {
            fetchRemoteTranslationFile(i18N, fileSystem, cacheDirPath, *codes)
        }

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

    suspend fun fetchRemoteTranslationFile(i18N: I18N, fileSystem: FileSystem?, cacheDirPath: String?, vararg codes: String){
        val client = HttpClient()

        val variant = mutableListOf<String>()
        val variantCombinations = mutableListOf<String>()
        codes.forEach { code ->
            variant.add(code)
            variantCombinations.add(variant.joinToString("."))
        }

        try {
            coroutineScope {
                val map = mutableMapOf<String, String>()

                variantCombinations.map { variantCombination ->
                    async {
                        client.get("https://fdca-70-28-93-175.ngrok-free.app/export") {
                            parameter("language", variantCombination)
                            parameter("project_id", "915cd661-ef3a-4b89-ac60-fb82ef90a61a")
//            parameter("document_path", "core")
                            parameter("document_format", "simple_json")
                        }.body<String>().let { jsonStringified ->
                            Json.decodeFromString<Map<String, String>>(jsonStringified)
                        }.let {



//                            fileSystem?.let {  fileSystemVal ->
//                                cacheDirPath?.let { cacheDirPathVal ->
//                                    val okioPath = "${cacheDirPathVal}/translations/".toPath()
//
//                                    val destinationPath = okioPath / "en.json"
//
//                                    val source: BufferedSource = fileSystemVal.source(destinationPath).buffer()
//                                    val result = source.readUtf8()
//                                    val x = Json.decodeFromString<Map<String, String>>(result)
//                                    println("@@@@ JSON before saving: ${x["tab_home"]}")
//                                }
//                            }




                            println("@@@ yipp")
                            saveTranslations(variantCombination, it, fileSystem, cacheDirPath)
//                            PlatformTranslationLoader.loadTranslationsFromDisk(variantCombination)
                            it
                        }
                    }
                }.awaitAll().forEach { translationMap ->
                    map.putAll(translationMap)
                }

                client.close()
                i18N.changeLocaleStrings(map)
            }
        } catch (_: Exception) {

        }
    }


//    fun saveTranslationsIGuess(path: String, map: Map<String, String>) {
//        println("@@ SAVE YEP")
//        val pathK = "README.md".toPath()
//
//        FileSystem.
//
//        val readmeContent = FileSystem.SYSTEM  .SYSTEM.read(pathK) {
//            readUtf8()
//        }
//
//        val updatedContent = readmeContent.replace("red", "blue")
//
//        FileSystem.SYSTEM.write(pathK) {
//            writeUtf8(updatedContent)
//        }
////        val jsonMap = encodeTranslationFile(map)
////        val filename = "translations.$path.json"
////
////        val basePath: Path = FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "trikot_data_source"
////
////        val fullPath = basePath / filename
////        FileSystem.SYSTEM_TEMPORARY_DIRECTORY
////        fullPath.parent?.let { FileSystem.createDirectories(it) }
////        fileSystem.write(fullPath) {
////            writeUtf8(value)
//        }
//
////        val file = File()
////        println("@@ ${file.absolutePath}")
////        file.writeText(jsonMap)
////        println("@@ SAVE END")
//    }

    fun saveTranslations(path: String, map: Map<String, String>, fileSystem: FileSystem?, cacheDirPath: String?) {
        val jsonMap = encodeTranslationFile(map)
        fileSystem?.let {  fileSystemVal ->
            cacheDirPath?.let { cacheDirPathVal ->
                val okioPath = "${cacheDirPathVal}/translations/".toPath()
                if(!fileSystemVal.exists(okioPath)){
                    println("@@ ---- $okioPath")
                    try {
                        fileSystemVal.createDirectory(okioPath, true)
                    } catch (e: Exception) {
                        println("@@ $e")
                    }
                }

                val destinationPath = okioPath / "en.json"
                fileSystemVal.write(destinationPath) {
                    writeUtf8(jsonMap)
                }

                val source: BufferedSource = fileSystemVal.source(destinationPath).buffer()
                val result = source.readUtf8()
                val x = Json.decodeFromString<Map<String, String>>(result)
                println("@@@@ JSON: ${x["tab_home"]}")
            }
        }




//        println("@@ ${FileSystem.SYSTEM.exists(okioPath)}")
//        val jsonMap = encodeTranslationFile(map)
//        val filename = "translations.$path.json"
//
//        val basePath: Path = FileSystem.SYSTEM.di / "trikot_data_source"
//
//        val fullPath = basePath / filename
//
//
//        fullPath.parent?.let { fileSystem.createDirectories(it) }
//        fileSystem.write(fullPath) {
//            writeUtf8(value)
//        }
//
//        FileSystem.SYSTEM

//        val file = File()
//        println("@@ ${file.absolutePath}")
//        file.writeText(jsonMap)
//        println("@@ SAVE END")
    }
}