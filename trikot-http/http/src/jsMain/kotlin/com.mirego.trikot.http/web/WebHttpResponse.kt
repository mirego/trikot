package com.mirego.trikot.http.web

import com.mirego.trikot.http.HttpResponse
import com.mirego.trikot.http.NativeBodyStringHttpResponse
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

/**
 * Browser TextDecoder, created once. null when unavailable (very old engines).
 */
private val utf8TextDecoder: dynamic = js(
    """(function() {
        try {
            return (typeof TextDecoder !== 'undefined') ? new TextDecoder('utf-8') : null;
        } catch (e) {
            return null;
        }
    })()"""
)

/**
 * Decode [buffer] as UTF-8 via the browser's native TextDecoder when available,
 * falling back to Kotlin's ByteArray.decodeToString().
 *
 * Native decoding avoids a full pure-Kotlin UTF-8 scan, which is expensive on low-end
 * TV browsers for big JSON bodies.
 */
internal fun decodeUtf8Body(buffer: ArrayBuffer): String {
    val decoder = utf8TextDecoder
    return if (decoder != null) {
        decoder.decode(buffer).unsafeCast<String>()
    } else {
        Int8Array(buffer, 0, buffer.byteLength).unsafeCast<ByteArray>().decodeToString()
    }
}

fun getResponseBodyString(xhr: XMLHttpRequest): String? {
    val response = xhr.response ?: return null
    return decodeUtf8Body(response.unsafeCast<ArrayBuffer>())
}

class WebHttpResponse(xhr: XMLHttpRequest) : HttpResponse, NativeBodyStringHttpResponse {
    private val responseBuffer: ArrayBuffer? =
        xhr.response?.unsafeCast<ArrayBuffer>()

    override val statusCode = xhr.status.toInt()
    override val headers: Map<String, String> = getResponseHeaders(xhr)
    override val bodyByteArray: ByteArray? by lazy {
        responseBuffer?.let { buffer ->
            Int8Array(buffer, 0, buffer.byteLength).unsafeCast<ByteArray>()
        }
    }
    override val nativeBodyString: String? by lazy {
        responseBuffer?.let { decodeUtf8Body(it) }
    }
    override val source: HttpResponse.ResponseSource = HttpResponse.ResponseSource.UNKNOWN
}
