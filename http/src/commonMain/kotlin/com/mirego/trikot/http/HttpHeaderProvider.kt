package com.mirego.trikot.http

import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Publisher

interface HttpHeaderProvider {
    /**
     * Retrieve a Headers publisher for a requestBuilder
     * @param cancellableManager CancellableManager that will be cancelled if the request is cancelled.
     * @param requestBuilder Request to send
     * @return Publisher of headers to send with the requestBuilder
     */
    fun headerForURLRequest(cancellableManager: CancellableManager, requestBuilder: RequestBuilder): Publisher<Map<String, String>>
    /**
     * This method is called on every error received by HttpRequestPublisher to let the HttpHeaderProvider modify, cleanup or refresh the headers for the next request.
     * @param requestBuilder Request that failed
     * @param error Error received from the HttpRequestPublisher
     */
    fun processHttpError(requestBuilder: RequestBuilder, error: Throwable)
}
