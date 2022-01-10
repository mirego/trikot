package com.mirego.trikot.streams.reactive.processors

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

typealias FilterProcessorBlock<T> = (T) -> Boolean

class FilterProcessor<T>(parentPublisher: Publisher<T>, private val block: FilterProcessorBlock<T>) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return FilterProcessorSubscription(subscriber, block)
    }

    class FilterProcessorSubscription<T>(subscriber: Subscriber<in T>, val block: FilterProcessorBlock<T>) :
        ProcessorSubscription<T, T>(subscriber) {
        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            if (block(t)) subscriber.onNext(t)
        }
    }
}
