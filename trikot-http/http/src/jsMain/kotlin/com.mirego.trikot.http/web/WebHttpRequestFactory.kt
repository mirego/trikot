package com.mirego.trikot.http.web

import com.mirego.trikot.http.HttpRequest
import com.mirego.trikot.http.HttpRequestFactory
import com.mirego.trikot.http.RequestBuilder

class WebHttpRequestFactory : HttpRequestFactory {
    override fun request(requestBuilder: RequestBuilder): HttpRequest {
        return WebHttpRequest(requestBuilder)
    }
}
