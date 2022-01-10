package com.mirego.trikot.http.web

import com.mirego.trikot.http.HttpResponse
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.xhr.XMLHttpRequest

fun getResponseBody(xhr: XMLHttpRequest) = xhr.response?.let {
    Int8Array(it as ArrayBuffer, 0, it.byteLength).unsafeCast<ByteArray>()
}

fun getResponseHeaders(xhr: XMLHttpRequest): Map<String, String> {
    return xhr.getAllResponseHeaders()
        .trim()
        .split(Regex("[\r\n]+"))
        .associate {
            val parts = it.split(": ")
            val name = parts.first()
            val value = parts.drop(1).joinToString(": ")
            name to value
        }
}

class WebHttpResponse(xhr: XMLHttpRequest) : HttpResponse {
    override val statusCode = xhr.status.toInt()
    override val headers: Map<String, String> = getResponseHeaders(xhr)
    override val bodyByteArray: ByteArray? = getResponseBody(xhr)
    override val source: HttpResponse.ResponseSource = HttpResponse.ResponseSource.UNKNOWN
}
