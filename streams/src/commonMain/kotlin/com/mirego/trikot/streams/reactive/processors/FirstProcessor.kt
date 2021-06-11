package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.AtomicReference
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

class FirstProcessor<T>(parentPublisher: Publisher<T>) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return FirstProcessorSubscription(subscriber)
    }

    class FirstProcessorSubscription<T>(s: Subscriber<in T>) : ProcessorSubscription<T, T>(s) {
        private val hasReceivedValue = AtomicReference(false)
        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            if (t != null) {
                if (hasReceivedValue.compareAndSet(false, true)) {
                    cancelActiveSubscription()
                    subscriber.onNext(t)
                    subscriber.onComplete()
                }
            }
        }

        override fun onComplete() {
            if (hasReceivedValue.compareAndSet(false, true)) {
                super.onComplete()
            }
        }
    }
}
