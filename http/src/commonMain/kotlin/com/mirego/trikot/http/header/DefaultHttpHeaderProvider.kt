package com.mirego.trikot.http.header

import com.mirego.trikot.http.HttpHeaderProvider
import com.mirego.trikot.http.RequestBuilder
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.PublisherFactory
import org.reactivestreams.Publisher

class DefaultHttpHeaderProvider : HttpHeaderProvider {
    override fun processHttpError(requestBuilder: RequestBuilder, error: Throwable) {
    }

    override fun headerForURLRequest(
        cancellableManager: CancellableManager,
        requestBuilder: RequestBuilder
    ): Publisher<Map<String, String>> {
        return PublisherFactory.create(HashMap())
    }
}
