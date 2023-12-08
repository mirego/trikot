package com.mirego.trikot.kword.remote.update

import android.content.Context
import com.mirego.trikot.kword.I18N
import okio.FileSystem

class KwordRemoteUpdate {
    private var fileSystem: FileSystem? = null
    private var cacheDirectoryPath: String? = null

    private var remoteTranslationsUrl: String? = null
    private var translationsVersion: String? = null

    fun setupFileSystem(fileSystem: FileSystem, context: Context) {
        this.fileSystem = fileSystem
        this.cacheDirectoryPath = context.cacheDir?.path
    }

    fun setupRemoteTranslationsSource(translationsUrl: String, translationsVersion: String) {
        remoteTranslationsUrl = translationsUrl
        this.translationsVersion = translationsVersion
    }

    fun updateTranslations(i18N: I18N, vararg codes: String) {
        TranslationsLoader.updateTranslations(i18N, fileSystem, cacheDirectoryPath, remoteTranslationsUrl, translationsVersion, *codes)
    }
}
