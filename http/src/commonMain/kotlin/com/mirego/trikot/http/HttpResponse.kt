package com.mirego.trikot.http

interface HttpResponse {
    /**
     * Http Code
     */
    val statusCode: Int
    /**
     * Body result as ByteArray
     */
    val bodyByteArray: ByteArray?
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
