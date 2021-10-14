package com.mirego.trikot.graphql

import com.mirego.trikot.foundation.concurrent.freeze
import com.mirego.trikot.http.ApplicationJSON
import com.mirego.trikot.http.ContentType
import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.http.HttpHeaderProvider
import com.mirego.trikot.http.HttpMethod
import com.mirego.trikot.http.RequestBuilder
import com.mirego.trikot.http.requestPublisher.DeserializableHttpRequestPublisher
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GraphqlQueryPublisher<T>(
    query: GraphqlQuery<T>,
    baseUrl: String = HttpConfiguration.baseUrl,
    path: String = "/graphql",
    httpHeaderProvider: HttpHeaderProvider = HttpConfiguration.defaultHttpHeaderProvider,
    requestTimeout: Duration? = null
) : DeserializableHttpRequestPublisher<T>(
    freeze(query.deserializer),
    createRequestBuilder(query.requestBody, baseUrl, path, requestTimeout),
    httpHeaderProvider
) {
    companion object {
        fun createRequestBuilder(body: String, baseUrl: String, path: String, requestTimeout: Duration? = null): RequestBuilder {
            return RequestBuilder().also {
                it.baseUrl = baseUrl
                it.path = path
                it.body = body
                it.method = HttpMethod.POST
                it.headers = mapOf(Pair(ContentType, ApplicationJSON))
                it.timeout = requestTimeout?.toInt(DurationUnit.SECONDS)
            }
        }
    }
}
