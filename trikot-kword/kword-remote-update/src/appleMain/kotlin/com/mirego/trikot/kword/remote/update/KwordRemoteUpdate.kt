package com.mirego.trikot.kword.remote.update

import com.mirego.trikot.kword.I18N
import kotlinx.cinterop.UnsafeNumber
import okio.FileSystem
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(UnsafeNumber::class)
class KwordRemoteUpdate {
    private var fileSystem: FileSystem? = null
    private var cacheDirectoryPath: String? = null

    private var remoteTranslationsUrl: String? = null
    private var translationsVersion: String? = null

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

    fun setupRemoteTranslationsSource(translationsUrl: String, translationsVersion: String) {
        remoteTranslationsUrl = translationsUrl
        this.translationsVersion = translationsVersion
    }

    fun updateTranslationsForLanguage(i18N: I18N, code: String) {
        TranslationsLoader.updateTranslations(i18N, fileSystem, cacheDirectoryPath, remoteTranslationsUrl, translationsVersion, code)
    }

    fun updateTranslationsForLanguages(i18N: I18N, vararg codes: String) {
        TranslationsLoader.updateTranslations(i18N, fileSystem, cacheDirectoryPath, remoteTranslationsUrl, translationsVersion, *codes)
    }
}
