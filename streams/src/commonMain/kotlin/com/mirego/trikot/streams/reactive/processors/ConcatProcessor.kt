package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class ConcatProcessor<T>(parentPublisher: Publisher<T>, private val nextPublisher: Publisher<T>) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return ConcatProcessorSubscription(subscriber, nextPublisher)
    }

    class ConcatProcessorSubscription<T>(
        private val subscriber: Subscriber<in T>,
        private val nextPublisher: Publisher<T>
    ) : ProcessorSubscription<T, T>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancel()
        }

        override fun onComplete() {
            nextPublisher.subscribe(
                cancellableManagerProvider.cancelPreviousAndCreate(),
                onNext = { subscriber.onNext(it) },
                onError = { subscriber.onError(it) },
                onCompleted = { subscriber.onComplete() }
            )
        }

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            subscriber.onNext(t)
        }
    }
}
