package com.mirego.trikot.kword.internal

import com.mirego.trikot.kword.I18N
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class RemoteTranslationsFetcher(
    private val i18N: I18N,
    private val baseTranslationsUrl: String?,
    private val translationsVersion: String?,
    private val internalStorageWrapper: InternalStorageWrapper
) {
    fun fetchRemoteTranslations(
        baseFileName: String,
        baseMap: MutableMap<String, String>,
        languageCodes: List<String>,
    ) {
        baseTranslationsUrl?.let { baseTranslationsUrlVal ->
            translationsVersion?.let { translationsVersionVal ->
                if (baseTranslationsUrlVal.isEmpty() || translationsVersionVal.isEmpty()) {
                    return
                }
                val httpClient = HttpClient()
                CoroutineScope(Dispatchers.Main).launch {
                    languageCodes.map { languageCode ->
                        async {
                            val translationsUrl = buildTranslationsUrl(baseTranslationsUrlVal, translationsVersionVal, languageCode, baseFileName)
                            fetchTranslation(httpClient, translationsUrl, baseFileName, languageCode)
                        }
                    }.awaitAll().forEach { result ->
                        result.fold(
                            onSuccess = {
                                baseMap.putAll(it)
                            },
                            onFailure = {}
                        )
                    }.also {
                        i18N.changeLocaleStrings(baseMap)
                    }
                    httpClient.close()
                }
            }
        }
    }

    private suspend fun fetchTranslation(httpClient: HttpClient, translationsUrl: String, baseFileName: String, languageCode: String): Result<Map<String, String>> {
        try {
            return httpClient.get(translationsUrl)
                .body<String>().let { encodedJson ->
                    Json.decodeFromString<Map<String, String>>(encodedJson)
                }.also { fetchedMap ->
                    internalStorageWrapper.saveTranslationsToCache(baseFileName, fetchedMap, languageCode)
                }.let {
                    Result.success(it)
                }
        } catch (e: ClientRequestException) {
            println(e)
            return Result.failure(e)
        } catch (e: SerializationException) {
            println(e)
            return Result.failure(e)
        } catch (e: IllegalArgumentException) {
            println(e)
            return Result.failure(e)
        } catch (e: NoTransformationFoundException) {
            println(e)
            return Result.failure(e)
        }
    }

    private fun buildTranslationsUrl(baseTranslationsUrl: String, translationsVersion: String, languageCode: String, baseFileName: String) =
        "$baseTranslationsUrl/$translationsVersion/$languageCode/$baseFileName.$languageCode.json"
}