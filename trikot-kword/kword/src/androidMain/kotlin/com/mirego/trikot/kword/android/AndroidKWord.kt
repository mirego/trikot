package com.mirego.trikot.kword.android

import android.content.Context
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KwordLoader
import com.mirego.trikot.kword.internal.PlatformTranslationSaver
import okio.FileSystem
import okio.Path.Companion.toPath

object AndroidKWord {
    private val DEFAULT_PATHS = listOf(
        "/translations/translation"
    )

    fun setCurrentLanguageCode(code: String, fileSystem: FileSystem? = null, context: Context? = null) {
        KwordLoader.setCurrentLanguageCode(code, DEFAULT_PATHS, fileSystem, context?.cacheDir?.path)
    }

    fun setCurrentLanguageCode(i18N: I18N, fileSystem: FileSystem? = null, context: Context? = null, code: String) {
        KwordLoader.setCurrentLanguageCode(i18N, DEFAULT_PATHS, fileSystem, context?.cacheDir?.path, code)
    }

    fun setCurrentLanguageCodes(i18N: I18N, fileSystem: FileSystem? = null, context: Context? = null, vararg codes: String) {
        KwordLoader.setCurrentLanguageCodes(i18N, DEFAULT_PATHS, fileSystem, context?.cacheDir?.path, *codes)
    }
}
