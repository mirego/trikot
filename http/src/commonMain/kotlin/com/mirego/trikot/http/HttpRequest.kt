package com.mirego.trikot.http

import com.mirego.trikot.streams.cancellable.CancellableManager
import kotlin.js.JsName
import org.reactivestreams.Publisher

interface HttpRequest {
    @JsName("execute")
    fun execute(cancellableManager: CancellableManager): Publisher<HttpResponse>
}
