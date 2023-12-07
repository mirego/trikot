package com.mirego.trikot.kword.remote.update

import com.mirego.trikot.kword.remote.update.internal.InternalCacheWrapper
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InternalCacheWrapperTests {

    companion object {
        const val internalStoragePath = "/"
        const val baseFileName = "translation"
        const val appVersion = "v1.0.0"
        const val englishLanguageCode = "en"
    }

    private lateinit var fileSystem: FakeFileSystem

    @BeforeTest
    fun setUp() {
        fileSystem = FakeFileSystem()
    }

    @Test
    fun givenFileSystemAndTranslationsThenMapIsSuccessfullySavedToDisk() {
        val internalCacheWrapper = InternalCacheWrapper(internalStoragePath, appVersion, fileSystem)
        val baseMap = mapOf(
            "foo_key" to "foo",
            "bar_key" to "bar"
        )
        internalCacheWrapper.saveTranslationsToCache(baseFileName, baseMap, englishLanguageCode)

        assertTrue { fileSystem.exists("translations/${appVersion}/${baseFileName}.${englishLanguageCode}.json".toPath()) }
    }

    @Test
    fun givenSavedTranslationsMapAndEmptyBaseMapThenLoadedMapIsIdentical() {
        val internalCacheWrapper = InternalCacheWrapper(internalStoragePath, appVersion, fileSystem)
        val savedTranslations = mapOf(
            "foo_key" to "foo",
            "bar_key" to "bar"
        )
        internalCacheWrapper.saveTranslationsToCache(baseFileName, savedTranslations, englishLanguageCode)

        val finalTranslationsMap = mutableMapOf<String, String>()
        internalCacheWrapper.loadTranslationsFromCache(baseFileName, finalTranslationsMap, listOf(englishLanguageCode))

        assertEquals(savedTranslations, finalTranslationsMap)
    }

    @Test
    fun givenDifferentSavedTranslationsMapAndBaseMapThenMapsAreMerged() {
        val internalCacheWrapper = InternalCacheWrapper(internalStoragePath, appVersion, fileSystem)
        val savedTranslations = mapOf(
            "foo_key" to "foo",
            "bar_key" to "bar"
        )
        internalCacheWrapper.saveTranslationsToCache(baseFileName, savedTranslations, englishLanguageCode)

        val finalTranslationsMap = mutableMapOf(
            "foo_key2" to "foo2",
            "bar_key2" to "bar2"
        )
        internalCacheWrapper.loadTranslationsFromCache(baseFileName, finalTranslationsMap, listOf(englishLanguageCode))

        assertEquals(
            mapOf(
                "foo_key" to "foo",
                "bar_key" to "bar",
                "foo_key2" to "foo2",
                "bar_key2" to "bar2"
            ), finalTranslationsMap
        )
    }

    @Test
    fun givenSavedTranslationsMapAndBaseMapWithSimilarKeysThenOriginalKeysAreOverwritten() {
        val internalCacheWrapper = InternalCacheWrapper(internalStoragePath, appVersion, fileSystem)
        val savedTranslations = mapOf(
            "foo_key" to "foo",
            "bar_key" to "bar"
        )
        internalCacheWrapper.saveTranslationsToCache(baseFileName, savedTranslations, englishLanguageCode)

        val finalTranslationsMap = mutableMapOf(
            "foo_key" to "foo1",
            "bar_key2" to "bar2"
        )
        internalCacheWrapper.loadTranslationsFromCache(baseFileName, finalTranslationsMap, listOf(englishLanguageCode))

        assertEquals(
            mapOf(
                "foo_key" to "foo",
                "bar_key" to "bar",
                "bar_key2" to "bar2"
            ), finalTranslationsMap
        )
    }
}
