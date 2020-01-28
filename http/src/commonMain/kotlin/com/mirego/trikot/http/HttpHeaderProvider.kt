package com.mirego.trikot.http

import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Publisher
import kotlin.js.JsName

interface HttpHeaderProvider {
    /**
     * Retreive a Headers publisher for a requestBuilder
     * @param cancellableManager CancellableManager that will be cancelled if the request is cancelled.
     * @param requestBuilder Request to sent
     * @return Publisher of headers to sent with the requestBuilder
     */
    @JsName("headerForURLRequest")
    fun headerForURLRequest(cancellableManager: CancellableManager, requestBuilder: RequestBuilder): Publisher<Map<String, String>>
    /**
     * This method is called on every error received by HttpRequestPublisher to let the HttpHeaderProvider modify, cleanup or refresh the headers for the next request.
     * @param requestBuilder Request that failed
     * @param error Error received from the HttpRequestPublisher
     */
    @JsName("processHttpError")
    fun processHttpError(requestBuilder: RequestBuilder, error: Throwable)
}
