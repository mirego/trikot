package com.mirego.trikot.http

interface HttpResponse {
    val statusCode: Int

    val bodyString: String?

    val headers: Map<String, String>

    val source: ResponseSource

    enum class ResponseSource {
        UNKNOWN,
        NETWORK,
        CACHE
    }
}
