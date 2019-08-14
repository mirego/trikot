package com.mirego.trikot.http

import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Publisher

interface HttpHeaderProvider {
    fun headerForURLRequest(cancellableManager: CancellableManager, requestBuilder: RequestBuilder): Publisher<Map<String, String>>
    fun processHttpError(requestBuilder: RequestBuilder, error: Throwable)
}
