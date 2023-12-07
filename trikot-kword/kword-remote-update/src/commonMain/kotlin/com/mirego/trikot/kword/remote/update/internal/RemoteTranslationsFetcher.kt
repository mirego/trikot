package com.mirego.trikot.kword.remote.update.internal

import com.mirego.trikot.kword.I18N
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
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
                            println("@@ $translationsUrl")
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

    fun applyFetchedTranslations(
        i18N: I18N,
        baseMap: MutableMap<String, String>,
        sharedHttpClient: HttpClient
    ) {
        coroutineScope.launch {
            requestsList.awaitAll()
                .forEach { result ->
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
                    sharedHttpClient.close()
                }
        }
    }

    private fun buildTranslationsUrl(baseTranslationsUrl: String, translationsVersion: String, languageCode: String, baseFileName: String) =
        "$baseTranslationsUrl/$translationsVersion/$baseFileName.$languageCode.json"
}
