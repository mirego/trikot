package com.mirego.trikot.kword.internal

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import okio.FileSystem

internal actual object PlatformTranslationSaver {
    actual fun saveTranslations(path: String, map: Map<String, String>, fileSystem: FileSystem?, cacheDirPath: String?) {
    }
}