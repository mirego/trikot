package com.mirego.trikot.streams.reactive.processors

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

class FirstProcessor<T>(parentPublisher: Publisher<T>) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return FirstProcessorSubscription(subscriber)
    }

    class FirstProcessorSubscription<T>(s: Subscriber<in T>) : ProcessorSubscription<T, T>(s) {

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            if (t != null) {
                cancelActiveSubscription()
                subscriber.onNext(t)
                subscriber.onComplete()
            }
        }
    }
}
