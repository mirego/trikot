package com.mirego.trikot.kword.internal

import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer


internal actual object PlatformTranslationSaver {
    actual fun saveTranslations(path: String, map: Map<String, String>, fileSystem: FileSystem?, cacheDirPath: String?) {
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
                println("@@@@ JSON: $result")
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