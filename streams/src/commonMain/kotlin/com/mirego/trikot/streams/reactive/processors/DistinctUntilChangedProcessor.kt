package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.concurrent.AtomicReference
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

class DistinctUntilChangedProcessor<T>(parentPublisher: Publisher<T>) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return DistinctUntilChangedProcessorSubscription(subscriber)
    }

    class DistinctUntilChangedProcessorSubscription<T>(subscriber: Subscriber<in T>) :
        ProcessorSubscription<T, T>(subscriber) {
        private val oldValueReference = AtomicReference<T?>(null)

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            if (t != oldValueReference.value) {
                oldValueReference.setOrThrow(oldValueReference.value, t)
                subscriber.onNext(t)
            }
        }
    }
}
