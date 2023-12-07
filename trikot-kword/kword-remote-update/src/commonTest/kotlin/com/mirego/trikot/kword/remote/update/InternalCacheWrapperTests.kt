package com.mirego.trikot.kword.remote.update

import com.mirego.trikot.kword.DefaultI18N
import com.mirego.trikot.kword.remote.update.internal.InternalCacheWrapper
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InternalCacheWrapperTests {

    companion object {
        const val INTERNAL_STORAGE_PATH = "/"
        const val BASE_FILE_NAME = "translation"
        const val APP_VERSION = "v1.0.0"
        const val ENGLISH_LANGUAGE_CODE = "en"
    }

    private lateinit var fileSystem: FakeFileSystem
    private val i18n = DefaultI18N()

    @BeforeTest
    fun setUp() {
        fileSystem = FakeFileSystem()
    }

    @Test
    fun givenFileSystemAndTranslationsThenMapIsSuccessfullySavedToDisk() {
        val internalCacheWrapper = InternalCacheWrapper(INTERNAL_STORAGE_PATH, APP_VERSION, fileSystem)
        val baseMap = mapOf(
            "foo_key" to "foo",
            "bar_key" to "bar"
        )
        internalCacheWrapper.saveTranslationsToCache(BASE_FILE_NAME, baseMap, ENGLISH_LANGUAGE_CODE)

        assertTrue { fileSystem.exists("translations/${APP_VERSION}/${BASE_FILE_NAME}.${ENGLISH_LANGUAGE_CODE}.json".toPath()) }
    }

    @Test
    fun givenSavedTranslationsMapAndEmptyBaseMapThenLoadedMapIsIdentical() {
        val internalCacheWrapper = InternalCacheWrapper(INTERNAL_STORAGE_PATH, APP_VERSION, fileSystem)
        val savedTranslations = mapOf(
            "foo_key" to "foo",
            "bar_key" to "bar"
        )
        internalCacheWrapper.saveTranslationsToCache(BASE_FILE_NAME, savedTranslations, ENGLISH_LANGUAGE_CODE)

        val finalTranslationsMap = mutableMapOf<String, String>()
        internalCacheWrapper.loadTranslationsFromCache(i18n, BASE_FILE_NAME, finalTranslationsMap, listOf(ENGLISH_LANGUAGE_CODE))

        assertEquals(savedTranslations, finalTranslationsMap)
    }

    @Test
    fun givenDifferentSavedTranslationsMapAndBaseMapThenMapsAreMerged() {
        val internalCacheWrapper = InternalCacheWrapper(INTERNAL_STORAGE_PATH, APP_VERSION, fileSystem)
        val savedTranslations = mapOf(
            "foo_key" to "foo",
            "bar_key" to "bar"
        )
        internalCacheWrapper.saveTranslationsToCache(BASE_FILE_NAME, savedTranslations, ENGLISH_LANGUAGE_CODE)

        val finalTranslationsMap = mutableMapOf(
            "foo_key2" to "foo2",
            "bar_key2" to "bar2"
        )
        internalCacheWrapper.loadTranslationsFromCache(i18n, BASE_FILE_NAME, finalTranslationsMap, listOf(ENGLISH_LANGUAGE_CODE))

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
        val internalCacheWrapper = InternalCacheWrapper(INTERNAL_STORAGE_PATH, APP_VERSION, fileSystem)
        val savedTranslations = mapOf(
            "foo_key" to "foo",
            "bar_key" to "bar"
        )
        internalCacheWrapper.saveTranslationsToCache(BASE_FILE_NAME, savedTranslations, ENGLISH_LANGUAGE_CODE)

        val finalTranslationsMap = mutableMapOf(
            "foo_key" to "foo1",
            "bar_key2" to "bar2"
        )
        internalCacheWrapper.loadTranslationsFromCache(i18n, BASE_FILE_NAME, finalTranslationsMap, listOf(ENGLISH_LANGUAGE_CODE))

        assertEquals(
            mapOf(
                "foo_key" to "foo",
                "bar_key" to "bar",
                "bar_key2" to "bar2"
            ), finalTranslationsMap
        )
    }
}
