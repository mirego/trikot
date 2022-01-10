package com.mirego.trikot.streams.reactive

import org.reactivestreams.Publisher

interface MutablePublisher<T> : Publisher<T> {
    var value: T?
    var error: Throwable?
    fun complete()
}
