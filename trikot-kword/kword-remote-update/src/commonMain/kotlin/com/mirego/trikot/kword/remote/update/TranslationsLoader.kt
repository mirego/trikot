package com.mirego.trikot.kword.remote.update

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.remote.update.internal.InternalCacheWrapper
import com.mirego.trikot.kword.remote.update.internal.RemoteTranslationsFetcher
import okio.FileSystem

internal object TranslationsLoader {
    private const val BASE_FILE_NAME = "translation"

    internal fun updateTranslations(
        i18N: I18N,
        fileSystem: FileSystem?,
        cacheDirectoryPath: String?,
        translationFileUrl: String?,
        translationsVersion: String?,
        vararg codes: String
    ) {
        val internalCacheWrapper = InternalCacheWrapper(cacheDirectoryPath, translationsVersion, fileSystem)
        val remoteTranslationsFetcher = RemoteTranslationsFetcher(translationFileUrl, translationsVersion, internalCacheWrapper)

        val languageCodes = mutableListOf<String>()
        val languageCodesCombinations = mutableListOf<String>()
        codes.forEach { code ->
            languageCodes.add(code)
            val variant = languageCodes.joinToString(".")
            languageCodesCombinations.add(variant)
        }

        val cachedTranslationsMap = mutableMapOf<String, String>()
        internalCacheWrapper.loadTranslationsFromCache(i18N, BASE_FILE_NAME, cachedTranslationsMap, languageCodesCombinations.toList())
        remoteTranslationsFetcher.fetchRemoteTranslations(i18N, BASE_FILE_NAME, cachedTranslationsMap, languageCodesCombinations.toList())
    }
}
