package com.mirego.trikot.streams.reactive.processors

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

typealias MapProcessorBlock<T, R> = (T) -> R

class MapProcessor<T, R>(parentPublisher: Publisher<T>, private val block: MapProcessorBlock<T, R>) :
    AbstractProcessor<T, R>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in R>): ProcessorSubscription<T, R> {
        return MapProcessorSubscription(subscriber, block)
    }

    class MapProcessorSubscription<T, R>(
        subscriber: Subscriber<in R>,
        private val block: MapProcessorBlock<T, R>
    ) : ProcessorSubscription<T, R>(subscriber) {
        override fun onNext(t: T, subscriber: Subscriber<in R>) {
            subscriber.onNext(block(t))
        }
    }
}
