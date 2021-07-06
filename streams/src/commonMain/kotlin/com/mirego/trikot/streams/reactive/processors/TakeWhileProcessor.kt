package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

typealias TakeWhileProcessorPredicate<T> = (T) -> Boolean

class TakeWhileProcessor<T>(
    parentPublisher: Publisher<T>,
    private val predicate: TakeWhileProcessorPredicate<T>
) : AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> =
        TakeWhileProcessorSubscription(subscriber, predicate)

    class TakeWhileProcessorSubscription<T>(
        subscriber: Subscriber<in T>,
        private val predicate: TakeWhileProcessorPredicate<T>
    ) : ProcessorSubscription<T, T>(subscriber) {

        private var hasCompleted: Boolean by atomic(false)

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            if (hasCompleted) return

            val result = try {
                predicate(t)
            } catch (exception: StreamsProcessorException) {
                onError(exception)
                return
            }

            if (result) {
                subscriber.onNext(t)
            } else {
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
