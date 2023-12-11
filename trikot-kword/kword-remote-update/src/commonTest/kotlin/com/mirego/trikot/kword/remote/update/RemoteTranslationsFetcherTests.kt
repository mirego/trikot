package com.mirego.trikot.kword.remote.update

import com.mirego.trikot.kword.DefaultI18N
import com.mirego.trikot.kword.remote.update.internal.InternalCacheWrapper
import com.mirego.trikot.kword.remote.update.internal.RemoteTranslationsFetcher
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RemoteTranslationsFetcherTests {

    companion object {
        const val INTERNAL_STORAGE_PATH = "/"
        const val BASE_TRANSLATIONS_URL = "www.translations-url.com/translations"
        const val BASE_FILE_NAME = "translation"
        const val TRANSLATIONS_VERSION = "v1.0.0"
        const val ENGLISH_LANGUAGE_CODE = "en"
    }

    private lateinit var fileSystem: FakeFileSystem
    private lateinit var internalCacheWrapper: InternalCacheWrapper
    private val i18n = DefaultI18N()

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        allowSpecialFloatingPointValues = true
    }

    @BeforeTest
    fun setUp() {
        fileSystem = FakeFileSystem()
        internalCacheWrapper = InternalCacheWrapper(INTERNAL_STORAGE_PATH, TRANSLATIONS_VERSION, fileSystem)
    }

    @Test
    fun givenEmptyBaseMapAndSuccessfulHttpRequestThenFinalMapEqualsHttpResponseMap() = runBlocking() {
        val remoteTranslationsFetcher = RemoteTranslationsFetcher(BASE_TRANSLATIONS_URL, TRANSLATIONS_VERSION, internalCacheWrapper, this)
        val baseMap = mutableMapOf<String, String>()
        val httpResponseMap = json.encodeToString(
            MapSerializer(String.serializer(), String.serializer()),
            mapOf(
                "foo_key" to "foo"
            )
        )

        remoteTranslationsFetcher.fetchRemoteTranslations(
            i18n,
            BASE_FILE_NAME,
            baseMap,
            listOf(ENGLISH_LANGUAGE_CODE),
            MockHttpEngineBuilder.buildEngine(HttpStatusCode.OK, httpResponseMap)
        )

        delay(5000)
        assertEquals(
            mapOf(
                "foo_key" to "foo"
            ),
            baseMap
        )
    }

    @Test
    fun givenBaseMapAndSuccessfulHttpRequestThenFinalMapEqualsMergedBaseMapAndHttpResponseMap() = runBlocking {
        val remoteTranslationsFetcher = RemoteTranslationsFetcher(BASE_TRANSLATIONS_URL, TRANSLATIONS_VERSION, internalCacheWrapper, this)
        val baseMap = mutableMapOf(
            "foo_key" to "foo",
            "bar_key" to "bar"
        )
        val httpResponseMap = json.encodeToString(
            MapSerializer(String.serializer(), String.serializer()),
            mapOf(
                "foo_bar_key" to "foo_bar"
            )
        )

        remoteTranslationsFetcher.fetchRemoteTranslations(
            i18n,
            BASE_FILE_NAME,
            baseMap,
            listOf(ENGLISH_LANGUAGE_CODE),
            MockHttpEngineBuilder.buildEngine(HttpStatusCode.OK, httpResponseMap)
        )

        delay(5000)
        assertEquals(
            mapOf(
                "foo_key" to "foo",
                "bar_key" to "bar",
                "foo_bar_key" to "foo_bar"
            ),
            baseMap
        )
    }

    @Test
    fun givenBaseMapAndFailedHttpRequestThenBaseMapIsUnchanged() = runBlocking {
        val remoteTranslationsFetcher = RemoteTranslationsFetcher(BASE_TRANSLATIONS_URL, TRANSLATIONS_VERSION, internalCacheWrapper, this)
        val baseMap = mutableMapOf(
            "foo_key" to "foo",
            "bar_key" to "bar"
        )

        remoteTranslationsFetcher.fetchRemoteTranslations(
            i18n,
            BASE_FILE_NAME,
            baseMap,
            listOf(ENGLISH_LANGUAGE_CODE),
            MockHttpEngineBuilder.buildEngine(HttpStatusCode.BadRequest)
        )

        delay(5000)
        assertEquals(
            mapOf(
                "foo_key" to "foo",
                "bar_key" to "bar"
            ),
            baseMap
        )
    }
}

private class MockHttpEngineBuilder {
    companion object {
        fun buildEngine(status: HttpStatusCode, jsonContent: String = "") = MockEngine {
            respond(
                content = jsonContent,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    }
}
