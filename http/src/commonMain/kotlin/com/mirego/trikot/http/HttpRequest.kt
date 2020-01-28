package com.mirego.trikot.http

import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Publisher
import kotlin.js.JsName

interface HttpRequest {
    @JsName("execute")
    fun execute(cancellableManager: CancellableManager): Publisher<HttpResponse>
}
