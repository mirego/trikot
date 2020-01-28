package com.mirego.trikot.http

import kotlin.js.JsName

interface HttpRequestFactory {
    /**
     * Create an HttpRequest for a request builder
     * @param requestBuilder RequestBuilder to create a HttpRequest for
     * @return A ready to be executed HttpRequest
     */
    @JsName("request")
    fun request(requestBuilder: RequestBuilder): HttpRequest
}
