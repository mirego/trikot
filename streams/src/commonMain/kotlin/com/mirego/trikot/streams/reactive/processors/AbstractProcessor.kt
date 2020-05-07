package com.mirego.trikot.streams.reactive.processors

import org.reactivestreams.Processor
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

abstract class AbstractProcessor<T, R>(
    val parentPublisher: Publisher<T>
) : Processor<T, R> {

    abstract fun createSubscription(subscriber: Subscriber<in R>): ProcessorSubscription<T, R>

    override fun subscribe(s: Subscriber<in R>) {
        parentPublisher.subscribe(createSubscription(s))
    }

    override fun onSubscribe(s: Subscription) {
    }

    override fun onError(t: Throwable) {
    }

    override fun onComplete() {
    }

    override fun onNext(t: T) {
    }
}
