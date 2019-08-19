package com.mirego.trikot.http

interface HttpRequestFactory {
    /**
     * Create an HttpRequest for a request builder
     * @param requestBuilder RequestBuilder to create a HttpRequest for
     * @return A ready to be executed HttpRequest
     */
    fun request(requestBuilder: RequestBuilder): HttpRequest
}
