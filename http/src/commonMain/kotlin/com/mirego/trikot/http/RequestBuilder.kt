package com.mirego.trikot.http

class RequestBuilder {
    var baseUrl: String? = null
    var path: String? = null
    var method = HttpMethod.GET
    var headers: Map<String, String> = HashMap()
    var body: Any? = null
    var cachePolicy: CachePolicy = CachePolicy.USE_PROTOCOL_CACHE_POLICY
    var timeout: Int = 30
    var followRedirects: Boolean = true
}
