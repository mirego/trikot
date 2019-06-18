package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

typealias WithChildCancelableManagerProcessorBlock<T> = (T, CancellableManager) -> T

class WithChildCancelableManagerProcessor<T>(
    parentPublisher: Publisher<T>,
    private val block: WithChildCancelableManagerProcessorBlock<T>
) :
    AbstractProcessor<T, T>(parentPublisher) {
    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return WithChildCancelableManagerSubscription(subscriber, block)
    }

    class WithChildCancelableManagerSubscription<T>(
        subscriber: Subscriber<in T>,
        private val block: WithChildCancelableManagerProcessorBlock<T>
    ) : ProcessorSubscription<T, T>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            subscriber.onNext(block(t, cancellableManagerProvider.cancelPreviousAndCreate()))
        }

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancel()
        }
    }
}
