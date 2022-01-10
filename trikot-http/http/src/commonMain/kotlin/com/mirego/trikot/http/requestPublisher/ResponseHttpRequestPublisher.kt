package com.mirego.trikot.http.requestPublisher

import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.http.HttpHeaderProvider
import com.mirego.trikot.http.HttpRequestFactory
import com.mirego.trikot.http.HttpResponse
import com.mirego.trikot.http.HttpResponseException
import com.mirego.trikot.http.RequestBuilder

open class ResponseHttpRequestPublisher(
    override val builder: RequestBuilder,
    headerProvider: HttpHeaderProvider = HttpConfiguration.defaultHttpHeaderProvider,
    httpRequestFactory: HttpRequestFactory = HttpConfiguration.httpRequestFactory
) : HttpRequestPublisher<HttpResponse>(headerProvider = headerProvider, httpRequestFactory = httpRequestFactory) {

    override fun processResponse(response: HttpResponse): HttpResponse {
        when (response.statusCode) {
            in 200..299 -> return response
            else -> throw HttpResponseException(response)
        }
    }
}
