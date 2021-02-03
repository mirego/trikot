package com.mirego.trikot.http

import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Publisher

interface HttpRequest {
    fun execute(cancellableManager: CancellableManager): Publisher<HttpResponse>
}
