package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

typealias TakeUntilProcessorPredicate<T> = (T) -> Boolean

class TakeUntilProcessor<T>(
    parentPublisher: Publisher<T>,
    private val predicate: TakeUntilProcessorPredicate<T>
) : AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> =
        TakeUntilProcessorSubscription(subscriber, predicate)

    class TakeUntilProcessorSubscription<T>(
        subscriber: Subscriber<in T>,
        private val predicate: TakeUntilProcessorPredicate<T>
    ) : ProcessorSubscription<T, T>(subscriber) {

        private var hasCompleted: Boolean by atomic(false)

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            if (hasCompleted) return
            subscriber.onNext(t)

            val result = try {
                predicate(t)
            } catch (exception: StreamsProcessorException) {
                onError(exception)
                return
            }

            if (result) {
                hasCompleted = true
                cancelActiveSubscription()
                subscriber.onComplete()
            }
        }

        override fun onComplete() {
            if (hasCompleted) return
            hasCompleted = true
            super.onComplete()
        }
    }
}
