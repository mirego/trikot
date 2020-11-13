package com.mirego.trikot.streams.reactive

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

class RetryablePublisher<T>(block: () -> Publisher<T>) : Publisher<T> {
    private val retryPublisher = Publishers.behaviorSubject(0)
    private val returnedPublisher = retryPublisher.switchMap {
        block()
    }.shared()

    fun retry() {
        retryPublisher.value = (retryPublisher.value ?: 0) + 1
    }

    override fun subscribe(s: Subscriber<in T>) {
        returnedPublisher.subscribe(s)
    }
}
