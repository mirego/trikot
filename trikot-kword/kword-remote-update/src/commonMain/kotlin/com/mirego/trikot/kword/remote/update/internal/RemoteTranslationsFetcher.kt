package com.mirego.trikot.kword.remote.update.internal

import com.mirego.trikot.kword.I18N
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.serialization.json.Json

internal class RemoteTranslationsFetcher(
    private val baseTranslationsUrl: String?,
    private val translationsVersion: String?,
    private val internalCacheWrapper: InternalCacheWrapper,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    fun fetchRemoteTranslations(
        i18N: I18N,
        baseFileName: String,
        baseMap: MutableMap<String, String>,
        languageCodes: List<String>,
        httpClientEngine: HttpClientEngine? = null
    ) {
        baseTranslationsUrl?.let { baseTranslationsUrlVal ->
            translationsVersion?.let { translationsVersionVal ->
                if (baseTranslationsUrlVal.isEmpty() || translationsVersionVal.isEmpty()) {
                    return
                }

                val sharedHttpClient = httpClientEngine?.let {
                    HttpClient(it)
                } ?: HttpClient()

                coroutineScope.launch {
                    supervisorScope {
                        languageCodes.map { languageCode ->
                            async {
                                val translationsUrl = buildTranslationsUrl(
                                    baseTranslationsUrlVal,
                                    translationsVersionVal,
                                    languageCode,
                                    baseFileName
                                )
                                fetchTranslations(sharedHttpClient, translationsUrl, baseFileName, languageCode)
                            }
                        }.awaitAll().also { requestsResults ->
                            applyFetchedTranslations(i18N, baseMap, requestsResults)
                            sharedHttpClient.close()
                        }
                    }
                }
            }
        }
    }

    private suspend fun fetchTranslations(
        httpClient: HttpClient,
        translationsUrl: String,
        baseFileName: String,
        languageCode: String
    ): Result<Map<String, String>> {
        try {
            httpClient.get(translationsUrl).let { response ->
                if (response.status.isSuccess()) {
                    return response.body<String>().let { encodedJson ->
                        Json.decodeFromString<Map<String, String>>(encodedJson)
                    }.let { fetchedMap ->
                        internalCacheWrapper.saveTranslationsToCache(baseFileName, fetchedMap, languageCode)
                        Result.success(fetchedMap)
                    }
                } else {
                    return Result.failure(IllegalStateException("Failed to fetch translations. HTTP status code: ${response.status.value}"))
                }
            }
        } catch (e: Exception) {
            println(e)
            return Result.failure(e)
        }
    }

    private fun applyFetchedTranslations(
        i18N: I18N,
        baseMap: MutableMap<String, String>,
        requestsResults: List<Result<Map<String, String>>>
    ) {
        requestsResults.forEach { result ->
            result.fold(
                onSuccess = {
                    baseMap.putAll(it)
                },
                onFailure = {}
            )
        }.also {
            if (baseMap.isNotEmpty()) {
                i18N.changeLocaleStrings(baseMap)
            }
        }
    }

    private fun buildTranslationsUrl(baseTranslationsUrl: String, translationsVersion: String, languageCode: String, baseFileName: String) =
        "${baseTranslationsUrl.trimEnd('/')}/$translationsVersion/$languageCode/$baseFileName.json"
}
