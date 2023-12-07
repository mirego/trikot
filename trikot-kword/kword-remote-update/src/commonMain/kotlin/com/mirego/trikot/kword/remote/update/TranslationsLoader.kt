package com.mirego.trikot.kword.remote.update

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.remote.update.internal.InternalCacheWrapper
import com.mirego.trikot.kword.remote.update.internal.RemoteTranslationsFetcher
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okio.FileSystem

internal object TranslationsLoader {
    val BASE_FILE_NAME = "translation"

    internal fun setCurrentLanguageCode(i18N: I18N, fileSystem: FileSystem?, cacheDirectoryPath: String?, translationFileUrl: String?, appVersion: String?, code: String) {
        setCurrentLanguageCodes(i18N, fileSystem, cacheDirectoryPath, translationFileUrl, appVersion, code)
    }

    internal fun setCurrentLanguageCodes(i18N: I18N, fileSystem: FileSystem?, cacheDirectoryPath: String?, translationFileUrl: String?, appVersion: String?, vararg codes: String) {
        val sharedHttpClient = HttpClient()
        val coroutineScope = CoroutineScope(Dispatchers.Default)

        val internalCacheWrapper = InternalCacheWrapper(cacheDirectoryPath, appVersion, fileSystem)
        val remoteTranslationsFetcher = RemoteTranslationsFetcher(translationFileUrl, appVersion, internalCacheWrapper, coroutineScope)

        val cachedTranslationsMap = mutableMapOf<String, String>()

        val variant = mutableListOf<String>()
        val variantCombinations = mutableListOf<String>()
        codes.forEach { code ->
            variant.add(code)
            val variantPath = variant.joinToString(".")
            variantCombinations.add(variantPath)
        }

//        internalCacheWrapper.loadTranslationsFromCache(BASE_FILE_NAME, cachedTranslationsMap, variantCombinations.toList())
        remoteTranslationsFetcher.fetchRemoteTranslations(BASE_FILE_NAME, variantCombinations.toList(), sharedHttpClient)

        if (cachedTranslationsMap.isNotEmpty()) {
            i18N.changeLocaleStrings(cachedTranslationsMap)
        }

 //       remoteTranslationsFetcher.applyFetchedTranslations(i18N, cachedTranslationsMap, sharedHttpClient)
    }
}