package com.mirego.trikot.http

import io.ktor.http.Parameters
import io.ktor.http.ParametersBuilder
import io.ktor.http.formUrlEncode

class RequestBuilder {
    /**
     * Base path
     * ex: http://www.mysite.com/api
     */
    var baseUrl: String? = null

    /**
     * Path to append to the base Path
     * ex: /users/1
     */
    var path: String? = null

    /**
     * HttpMethod
     */
    var method = HttpMethod.GET

    /**
     * Headers to send the request with. Will be merged with the headers provided by the HttpHeaderProvider.
     */
    var headers: Map<String, String> = HashMap()

    /**
     * Body to send the request with. Only String are supported for now.
     */
    var body: Any? = null

    /**
     * Unsupported
     */
    var cachePolicy: CachePolicy = CachePolicy.USE_PROTOCOL_CACHE_POLICY

    /**
     * Timeout (in seconds) that needs to be applied to this specific request.
     * If null, the default configured timeout will be used
     */
    var timeout: Int? = null

    /**
     * Unsupported
     */
    var followRedirects: Boolean = true

    /**
     * Url query parameters
     */
    var parameters: Map<String, String> = HashMap()

    /**
     * Build the request Url using base url, path and parameters
     *
     * Parameters are encoded by default
     */
    fun buildUrl(): String {
        return (baseUrl ?: "") + buildPath()
    }

    private fun buildPath(): String {
        val queryParameters = buildParameters()

        return (path ?: "") + (
            when (queryParameters) {
                null -> ""
                else -> {
                    val prefix = when (path?.contains("?")) {
                        true -> "&"
                        else -> "?"
                    }
                    "$prefix${queryParameters.formUrlEncode()}"
                }
            }
            )
    }

    private fun buildParameters(): Parameters? {
        return when (parameters.isEmpty()) {
            true -> null
            else -> ParametersBuilder()
                .also { builder ->
                    parameters.forEach {
                        builder.append(it.key, it.value)
                    }
                }
                .build()
        }
    }
}
