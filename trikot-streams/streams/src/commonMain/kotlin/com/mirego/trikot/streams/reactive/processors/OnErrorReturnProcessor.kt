package com.mirego.trikot.streams.reactive.processors

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

typealias OnErrorReturnProcessorBlock<T> = (Throwable) -> T

class OnErrorReturnProcessor<T>(parentPublisher: Publisher<T>, private val block: OnErrorReturnProcessorBlock<T>) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return OnErrorReturnSubscription(subscriber, block)
    }

    class OnErrorReturnSubscription<T>(
        private val subscriber: Subscriber<in T>,
        private val block: OnErrorReturnProcessorBlock<T>
    ) : ProcessorSubscription<T, T>(subscriber) {

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            subscriber.onNext(t)
        }

        override fun onError(t: Throwable) {
            cancelActiveSubscription()
            subscriber.onNext(block(t))
        }
    }
}
