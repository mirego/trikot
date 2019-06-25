package com.mirego.trikot.streams.reactive.processors

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

typealias MapErrorAsNextProcessorBlock<T> = (Throwable) -> T

class MapErrorAsNextProcessor<T>(parentPublisher: Publisher<T>, private val block: MapErrorAsNextProcessorBlock<T>) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return MapErrorAsNextProcessorSubscription(subscriber, block)
    }

    class MapErrorAsNextProcessorSubscription<T>(
        private val subscriber: Subscriber<in T>,
        private val block: MapErrorAsNextProcessorBlock<T>
    ) : ProcessorSubscription<T, T>(subscriber) {

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            subscriber.onNext(t)
        }

        override fun onError(t: Throwable) {
            subscriber.onNext(block(t))
            subscriber.onComplete()
        }
    }
}
