package com.mirego.trikot.kword.remote.update

import android.content.Context
import com.mirego.trikot.kword.I18N
import okio.FileSystem

actual object KwordRemoteUpdate {
    private var fileSystem: FileSystem? = null
    private var cacheDirectoryPath: String? = null

    private var remoteTranslationsUrl: String? = null
    private var appVersion: String? = null

    fun setupFileSystem(fileSystem: FileSystem, context: Context) {
        this.fileSystem = fileSystem
        this.cacheDirectoryPath = context.cacheDir?.path
    }

    fun setupRemoteTranslationsSource(translationsUrl: String, appVersion: String) {
        remoteTranslationsUrl = translationsUrl
        this.appVersion = appVersion
    }

    fun setCurrentLanguageCodes(i18N: I18N, vararg codes: String) {
        TranslationsLoader.setCurrentLanguageCodes(i18N, fileSystem, cacheDirectoryPath, remoteTranslationsUrl, appVersion, *codes)
    }
}
