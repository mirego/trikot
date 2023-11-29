package com.mirego.trikot.kword.internal

import com.mirego.trikot.kword.KWord
import java.io.File
import java.io.IOException

internal actual object PlatformTranslationLoader {
    actual fun loadTranslations(path: String): Map<String, String>? =
        try {
            KWord::class.java.getResource(path)
                ?.readText()
                ?.let { decodeTranslationFile(it) }
        } catch (ioException: IOException) {
            null
        }
}