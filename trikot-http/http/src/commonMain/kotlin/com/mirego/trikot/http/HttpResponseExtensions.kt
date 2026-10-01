package com.mirego.trikot.http

/**
 * Body result as a UTF-8 string.
 *
 * When [HttpConfiguration.useNativeBodyStringDecoder] is true and the response implements
 * [NativeBodyStringHttpResponse], uses that path.
 * Otherwise decodes [HttpResponse.bodyByteArray] with Kotlin's UTF-8 decoder.
 */
val HttpResponse.bodyString: String?
    get() {
        if (HttpConfiguration.useNativeBodyStringDecoder) {
            (this as? NativeBodyStringHttpResponse)?.nativeBodyString?.let { return it }
        }
        return bodyByteArray?.decodeToString()
    }
