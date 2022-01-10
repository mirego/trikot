package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

typealias WithCancellableManagerProcessorResultType<T> = Pair<CancellableManager, T>

class WithCancellableManagerProcessor<T>(
    parentPublisher: Publisher<T>
) :
    AbstractProcessor<T, WithCancellableManagerProcessorResultType<T>>(parentPublisher) {
    override fun createSubscription(subscriber: Subscriber<in WithCancellableManagerProcessorResultType<T>>): ProcessorSubscription<T, WithCancellableManagerProcessorResultType<T>> {
        return WithCancellableManagerSubscription(subscriber)
    }

    class WithCancellableManagerSubscription<T>(
        subscriber: Subscriber<in WithCancellableManagerProcessorResultType<T>>
    ) : ProcessorSubscription<T, Pair<CancellableManager, T>>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()

        override fun onNext(t: T, subscriber: Subscriber<in WithCancellableManagerProcessorResultType<T>>) {
            subscriber.onNext(cancellableManagerProvider.cancelPreviousAndCreate() to t)
        }

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancel()
        }
    }
}
