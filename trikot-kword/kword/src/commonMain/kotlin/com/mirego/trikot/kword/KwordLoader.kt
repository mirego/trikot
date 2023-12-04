package com.mirego.trikot.kword

import com.mirego.trikot.kword.internal.InternalStorageWrapper
import com.mirego.trikot.kword.internal.PlatformTranslationLoader
import com.mirego.trikot.kword.internal.RemoteTranslationsFetcher
import okio.FileSystem

object KwordLoader {
    fun setCurrentLanguageCode(code: String, basePaths: List<String>, fileSystem: FileSystem?, internalStoragePath: String?, translationFileUrl: String?, appVersion: String?) {
        setCurrentLanguageCodes(KWord, basePaths, fileSystem, internalStoragePath, translationFileUrl, appVersion, code)
    }

    fun setCurrentLanguageCode(i18N: I18N, basePaths: List<String>, fileSystem: FileSystem?, cacheDirPath: String?, translationFileUrl: String?, appVersion: String?, code: String) {
        setCurrentLanguageCodes(i18N, basePaths, fileSystem, cacheDirPath, translationFileUrl, appVersion, code)
    }

    fun setCurrentLanguageCodes(i18N: I18N, basePaths: List<String>, fileSystem: FileSystem?, cacheDirPath: String?, translationFileUrl: String?, appVersion: String?, vararg codes: String) {
        val internalStorageWrapper = InternalStorageWrapper(cacheDirPath, appVersion, fileSystem)
        val remoteTranslationsFetcher = RemoteTranslationsFetcher(i18N, translationFileUrl, appVersion, internalStorageWrapper)

        val bundledTranslationsMap = mutableMapOf<String, String>()
        val cachedTranslationsMap = mutableMapOf<String, String>()
        val remoteTranslationsMap = mutableMapOf<String, String>()

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
            internalStorageWrapper.loadTranslationsFromCache(baseFileName, cachedTranslationsMap, variantCombinations.toList())
            remoteTranslationsFetcher.fetchRemoteTranslations(baseFileName, remoteTranslationsMap, variantCombinations.toList())
        }

        bundledTranslationsMap.putAll(cachedTranslationsMap)
        i18N.changeLocaleStrings(bundledTranslationsMap)

    }
}