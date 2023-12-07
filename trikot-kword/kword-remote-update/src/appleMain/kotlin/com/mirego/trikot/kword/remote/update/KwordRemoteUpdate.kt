package com.mirego.trikot.kword.remote.update

import com.mirego.trikot.kword.I18N
import kotlinx.cinterop.UnsafeNumber
import okio.FileSystem
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(UnsafeNumber::class)
actual object KwordRemoteUpdate {
    private var fileSystem: FileSystem? = null
    private var cacheDirectoryPath: String? = null

    private var remoteTranslationsUrl: String? = null
    private var appVersion: String? = null

    fun setupFileSystem(fileSystem: FileSystem, fileManager: NSFileManager) {
        val cacheDirectoryPath = fileManager.URLsForDirectory(
            NSDocumentDirectory,
            NSUserDomainMask
        ).firstOrNull() as? NSURL

        cacheDirectoryPath?.relativePath?.let {
            this.cacheDirectoryPath = it
            this.fileSystem = fileSystem
        }
    }

    fun setupRemoteTranslationsSource(translationsUrl: String, appVersion: String) {
        remoteTranslationsUrl = translationsUrl
        this.appVersion = appVersion
    }

    fun setCurrentLanguageCode(i18N: I18N, code: String) {
        TranslationsLoader.setCurrentLanguageCodes(i18N, fileSystem, cacheDirectoryPath, remoteTranslationsUrl, appVersion, code)
    }

    fun setCurrentLanguageCodes(i18N: I18N, vararg codes: String) {
        TranslationsLoader.setCurrentLanguageCodes(i18N, fileSystem, cacheDirectoryPath, remoteTranslationsUrl, appVersion, *codes)
    }
}