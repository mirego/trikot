package com.mirego.trikot.kword.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class RemoteTranslationsFetcher(
    private val baseTranslationsUrl: String?,
    private val translationsVersion: String?,
    private val internalCacheWrapper: InternalCacheWrapper,
    private val coroutineScope: CoroutineScope
) {
    val requestsList = mutableListOf<Deferred<Result<Map<String, String>>>>()

    fun fetchRemoteTranslations(
        baseFileName: String,
        languageCodes: List<String>,
        httpClient: HttpClient
    ) {
        baseTranslationsUrl?.let { baseTranslationsUrlVal ->
            translationsVersion?.let { translationsVersionVal ->
                if (baseTranslationsUrlVal.isEmpty() || translationsVersionVal.isEmpty()) {
                    return
                }

                coroutineScope.launch {
                    languageCodes.map { languageCode ->
                        async {
                            val translationsUrl = buildTranslationsUrl(
                                baseTranslationsUrlVal,
                                translationsVersionVal,
                                languageCode,
                                baseFileName
                            )
                            buildHttpRequest(httpClient, translationsUrl, baseFileName, languageCode)
                        }
                    }.also {
                        requestsList.addAll(it)
                    }
                }
            }
        }
    }

    private suspend fun buildHttpRequest(
        httpClient: HttpClient,
        translationsUrl: String,
        baseFileName: String,
        languageCode: String
    ): Result<Map<String, String>> {
        try {
            return httpClient.get(translationsUrl)
                .body<String>().let { encodedJson ->
                    Json.decodeFromString<Map<String, String>>(encodedJson)
                }.let { fetchedMap ->
                    internalCacheWrapper.saveTranslationsToCache(baseFileName, fetchedMap, languageCode)
                    Result.success(fetchedMap)
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