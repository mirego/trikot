package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.observeOn
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class MergeProcessor<T>(parentPublisher: Publisher<T>, private val publishers: List<Publisher<out T>>) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return MergeProcessorSubscription(subscriber, publishers)
    }

    class MergeProcessorSubscription<T>(
        private val subscriber: Subscriber<in T>,
        private val publishers: List<Publisher<out T>>
    ) : ProcessorSubscription<T, T>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()
        private val incompleteCount = AtomicReference(publishers.size + 1)
        private val serialQueue = SynchronousSerialQueue()
        private val hasSubscribed = AtomicReference(false)

        override fun onSubscribe(s: Subscription) {
            super.onSubscribe(s)
            subscribeToCombinedPublishersIfNeeded()
        }

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancel()
            hasSubscribed.compareAndSet(true, false)
        }

        override fun onComplete() {
            dispatchCompletedIfAllCompleted()
        }

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            subscriber.onNext(t)
        }

        override fun onError(t: Throwable) {
            super.onError(t)
            cancellableManagerProvider.cancelPreviousAndCreate()
            cancelActiveSubscription()
        }

        private fun subscribeToCombinedPublishersIfNeeded() {
            if (hasSubscribed.compareAndSet(false, true)) {
                val cancellableManager = cancellableManagerProvider.cancelPreviousAndCreate()

                publishers.forEach { publisher ->
                    publisher.observeOn(serialQueue).subscribe(
                        cancellableManager,
                        onNext = { onNext(it) },
                        onError = { onError(it) },
                        onCompleted = { dispatchCompletedIfAllCompleted() }
                    )
                }
            }
        }

        private fun dispatchCompletedIfAllCompleted() {
            serialQueue.dispatch {
                val newValue = incompleteCount.value - 1
                incompleteCount.setOrThrow(incompleteCount.value, newValue)
                if (newValue == 0) {
                    subscriber.onComplete()
                }
            }
        }
    }
}
