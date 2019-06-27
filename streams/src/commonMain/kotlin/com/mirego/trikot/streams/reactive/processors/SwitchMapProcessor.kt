package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

typealias SwitchMapProcessorBlock<T, R> = (T) -> Publisher<R>

class SwitchMapProcessor<T, R>(parentPublisher: Publisher<T>, private var block: SwitchMapProcessorBlock<T, R>) :
    AbstractProcessor<T, R>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in R>): ProcessorSubscription<T, R> {
        return SwitchMapProcessorSubscription(subscriber, block)
    }

    class SwitchMapProcessorSubscription<T, R>(
        subscriber: Subscriber<in R>,
        private val block: SwitchMapProcessorBlock<T, R>
    ) : ProcessorSubscription<T, R>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancel()
        }

        override fun onComplete() {
            // By design, completion should be handled by a subclass of SwitchMapProcessor
        }

        override fun onNext(t: T, subscriber: Subscriber<in R>) {
            block(t).subscribe(cancellableManagerProvider.cancelPreviousAndCreate(),
                onNext = { subscriber.onNext(it) },
                onError = { subscriber.onError(it) })
        }
    }
}
