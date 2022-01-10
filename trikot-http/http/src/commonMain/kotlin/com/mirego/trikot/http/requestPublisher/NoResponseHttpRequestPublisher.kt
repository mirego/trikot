package com.mirego.trikot.http.requestPublisher

import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.http.HttpHeaderProvider
import com.mirego.trikot.http.HttpResponse
import com.mirego.trikot.http.HttpResponseException
import com.mirego.trikot.http.RequestBuilder

open class NoResponseHttpRequestPublisher(
    override val builder: RequestBuilder,
    headerProvider: HttpHeaderProvider = HttpConfiguration.defaultHttpHeaderProvider
) : HttpRequestPublisher<Unit>(headerProvider = headerProvider) {
    override fun processResponse(response: HttpResponse) {
        when (response.statusCode) {
            in 200..299 -> return
            else -> throw HttpResponseException(response)
        }
    }
}
