package com.mirego.trikot.http

interface HttpRequestFactory {
    fun request(requestBuilder: RequestBuilder): HttpRequest
}
