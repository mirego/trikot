package com.mirego.trikot.streams.reactive

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class NeverPublisher<T> : Publisher<T> {
    override fun subscribe(s: Subscriber<in T>) {
        s.onSubscribe(
            object : Subscription {
                override fun request(n: Long) {}

                override fun cancel() {
                }
            }
        )
    }
}
