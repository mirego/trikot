package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancelable.ResettableCancelableManager
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
        private val resetableCancelableManager = ResettableCancelableManager()

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            resetableCancelableManager.cancel()
        }

        override fun onNext(t: T, subscriber: Subscriber<in R>) {
            block(t).subscribe(resetableCancelableManager.reset(),
                onNext = { subscriber.onNext(it) },
                onError = { subscriber.onError(it) })
        }
    }
}
