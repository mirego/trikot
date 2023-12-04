package com.mirego.trikot.kword

import com.mirego.trikot.kword.internal.InternalCacheWrapper
import com.mirego.trikot.kword.internal.PlatformTranslationLoader
import com.mirego.trikot.kword.internal.RemoteTranslationsFetcher
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import okio.FileSystem

object KwordLoader {
    fun setCurrentLanguageCode(code: String, basePaths: List<String>, fileSystem: FileSystem?, internalStoragePath: String?, translationFileUrl: String?, appVersion: String?) {
        setCurrentLanguageCodes(KWord, basePaths, fileSystem, internalStoragePath, translationFileUrl, appVersion, code)
    }

    fun setCurrentLanguageCode(i18N: I18N, basePaths: List<String>, fileSystem: FileSystem?, cacheDirPath: String?, translationFileUrl: String?, appVersion: String?, code: String) {
        setCurrentLanguageCodes(i18N, basePaths, fileSystem, cacheDirPath, translationFileUrl, appVersion, code)
    }

    fun setCurrentLanguageCodes(i18N: I18N, basePaths: List<String>, fileSystem: FileSystem?, cacheDirPath: String?, translationFileUrl: String?, appVersion: String?, vararg codes: String) {
        val sharedHttpClient = HttpClient()
        val coroutineScope = CoroutineScope(Dispatchers.Unconfined)

        val internalCacheWrapper = InternalCacheWrapper(cacheDirPath, appVersion, fileSystem)
        val remoteTranslationsFetcher = RemoteTranslationsFetcher(translationFileUrl, appVersion, internalCacheWrapper, coroutineScope)

        val bundledTranslationsMap = mutableMapOf<String, String>()
        val cachedTranslationsMap = mutableMapOf<String, String>()

        basePaths.forEach { basePath ->
            val variant = mutableListOf<String>()
            val variantCombinations = mutableListOf<String>()
            codes.forEach { code ->
                variant.add(code)
                val variantPath = variant.joinToString(".")
                variantCombinations.add(variantPath)
                val filePath = "$basePath.$variantPath.json"
                PlatformTranslationLoader.loadTranslations(filePath)
                    ?.let { bundledTranslationsMap.putAll(it) }
                    ?: run { println("path not found $filePath") }
            }

            val baseFileName = basePath.split("/").last()
            internalCacheWrapper.loadTranslationsFromCache(baseFileName, cachedTranslationsMap, variantCombinations.toList())
            remoteTranslationsFetcher.fetchRemoteTranslations(baseFileName, variantCombinations.toList(), sharedHttpClient)
        }

        bundledTranslationsMap.putAll(cachedTranslationsMap)
        i18N.changeLocaleStrings(bundledTranslationsMap)
        applyFetchedTranslations(i18N, remoteTranslationsFetcher.requestsList, bundledTranslationsMap, sharedHttpClient, coroutineScope)
    }

    private fun applyFetchedTranslations(
        i18N: I18N,
        listOfRequests: List<Deferred<Result<Map<String, String>>>>,
        baseMap: MutableMap<String, String>,
        sharedHttpClient: HttpClient,
        coroutineScope: CoroutineScope
    ) {
        coroutineScope.launch {
            listOfRequests.awaitAll()
                .forEach { result ->
                    result.fold(
                        onSuccess = {
                            baseMap.putAll(it)
                        },
                        onFailure = {}
                    )
                }.also {
                    i18N.changeLocaleStrings(baseMap)
                    sharedHttpClient.close()
                }
        }
    }
}