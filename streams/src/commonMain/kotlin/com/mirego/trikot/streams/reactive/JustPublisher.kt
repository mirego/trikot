package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.concurrent.AtomicReference
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class JustPublisher<T>(private val values: Iterable<T>) : Publisher<T> {
    override fun subscribe(s: Subscriber<in T>) {
        val isCancelled = AtomicReference(false)
        s.onSubscribe(
            object : Subscription {
                override fun request(n: Long) {}

                override fun cancel() {
                    isCancelled.compareAndSet(false, true)
                }
            }
        )
        if (!isCancelled.value) {
            for (value in values) {
                s.onNext(value)
            }
        }
        if (!isCancelled.value) {
            s.onComplete()
        }
    }
}
