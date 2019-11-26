package com.mirego.trikot.http.requestPublisher

import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.http.HttpHeaderProvider
import com.mirego.trikot.http.HttpResponse
import com.mirego.trikot.http.HttpResponseException
import com.mirego.trikot.http.RequestBuilder
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.UnstableDefault

open class DeserializableHttpRequestPublisher<T>(
    private val deserializer: DeserializationStrategy<T>,
    override val builder: RequestBuilder,
    headerProvider: HttpHeaderProvider = HttpConfiguration.defaultHttpHeaderProvider
) : HttpRequestPublisher<T>(headerProvider = headerProvider) {

    @UseExperimental(UnstableDefault::class)
    override fun processResponse(response: HttpResponse): T {
        when (response.statusCode) {
            in 200..299 -> return successfulResponse(response)
            else -> throw HttpResponseException(response)
        }
    }

    private fun successfulResponse(response: HttpResponse): T {
        response.bodyString?.let { bodyString ->
            return HttpConfiguration.json.parse(deserializer, bodyString)
        }
        throw IllegalStateException("Cannot process an empty bodyString response")
    }
}
