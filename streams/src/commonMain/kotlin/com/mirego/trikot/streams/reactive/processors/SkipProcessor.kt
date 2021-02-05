package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.AtomicReference
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import kotlin.math.max

class SkipProcessor<T>(parentPublisher: Publisher<T>, private val n: Long) : AbstractProcessor<T, T>(parentPublisher) {
    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return SkipProcessorSubscription(subscriber, n)
    }

    class SkipProcessorSubscription<T>(s: Subscriber<in T>, n: Long) : ProcessorSubscription<T, T>(s) {
        private val remaining = AtomicReference(max(0, n))

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            if (remaining.value != 0L) {
                remaining.setOrThrow(remaining.value, remaining.value - 1)
            } else {
                subscriber.onNext(t)
            }
        }
    }
}
