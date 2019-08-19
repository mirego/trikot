package com.mirego.trikot.http

interface HttpResponse {
    /**
     * Http Code
     */
    val statusCode: Int
    /**
     * Body result as String
     * Note: In the future version, we should have a bodyStream (inputStream) instead of bodyString
     */
    val bodyString: String?
    /**
     * Result header
     */
    val headers: Map<String, String>
    /**
     * ResponseSource (See below)
     */
    val source: ResponseSource

    enum class ResponseSource {
        UNKNOWN,
        NETWORK,
        CACHE
    }
}
