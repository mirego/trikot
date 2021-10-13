package com.mirego.trikot.http

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
}
