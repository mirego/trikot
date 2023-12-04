package com.mirego.trikot.kword.android

import android.content.Context
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KwordLoader
import okio.FileSystem

object AndroidKWord {
    private val DEFAULT_PATHS = listOf(
        "/translations/translation"
    )

    private var fileSystem: FileSystem? = null
    private var storageDirPath: String? = null

    private var remoteTranslationsUrl: String? = null
    private var appVersion: String? = null

    fun setupFileSystem(fileSystem: FileSystem, context: Context) {
        this.fileSystem = fileSystem
        this.storageDirPath = context.cacheDir?.path
    }

    fun setupRemoteTranslationsSource(translationsUrl: String, appVersion: String) {
        remoteTranslationsUrl = translationsUrl
        this.appVersion = appVersion
    }

    fun setCurrentLanguageCode(code: String) {
        KwordLoader.setCurrentLanguageCode(code, DEFAULT_PATHS, fileSystem, storageDirPath, remoteTranslationsUrl, appVersion)
    }

    fun setCurrentLanguageCode(i18N: I18N, code: String) {
        KwordLoader.setCurrentLanguageCode(i18N, DEFAULT_PATHS, fileSystem, storageDirPath, remoteTranslationsUrl, appVersion, code)
    }

    fun setCurrentLanguageCodes(i18N: I18N, vararg codes: String) {
        KwordLoader.setCurrentLanguageCodes(i18N, DEFAULT_PATHS, fileSystem, storageDirPath, remoteTranslationsUrl, appVersion, *codes)
    }
}
