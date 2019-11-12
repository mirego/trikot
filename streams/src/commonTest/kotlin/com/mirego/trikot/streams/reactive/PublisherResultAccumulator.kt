package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Publisher

class PublisherResultAccumulator<T>(publisher: Publisher<T>) : Cancellable {
    val values = ArrayList<T>()
    var error: Throwable? = null
    var completed = false
    val cancellableManager = CancellableManager()

    init {
        publisher.subscribe(cancellableManager,
            onNext = { values.add(it) },
            onError = { error = it },
            onCompleted = { completed = true })
    }

    override fun cancel() {
        cancellableManager.cancel()
    }
}
