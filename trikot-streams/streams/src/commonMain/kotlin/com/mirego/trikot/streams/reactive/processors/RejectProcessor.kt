package com.mirego.trikot.streams.reactive.processors

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

typealias RejectProcessorBlock<T> = (T) -> Boolean

class RejectProcessor<T>(parentPublisher: Publisher<T>, private val block: RejectProcessorBlock<T>) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return RejectProcessorSubscription(subscriber, block)
    }

    class RejectProcessorSubscription<T>(subscriber: Subscriber<in T>, val block: RejectProcessorBlock<T>) :
        ProcessorSubscription<T, T>(subscriber) {
        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            if (!block(t)) subscriber.onNext(t)
        }
    }
}
