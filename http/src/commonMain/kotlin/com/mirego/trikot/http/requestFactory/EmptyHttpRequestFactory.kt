package com.mirego.trikot.http.requestFactory

import com.mirego.trikot.http.HttpRequest
import com.mirego.trikot.http.HttpRequestFactory
import com.mirego.trikot.http.RequestBuilder

class EmptyHttpRequestFactory : HttpRequestFactory {
    override fun request(requestBuilder: RequestBuilder): HttpRequest {
        TODO(
            "HTTPConfiguration.httpRequestFactory must be set before sending request." +
                " See: https://github.com/mirego/trikot.http/blob/master/README.md"
        )
    }
}
