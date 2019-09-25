package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.AtomicListReference
import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class CombineLatestProcessor<T>(
    parentPublisher: Publisher<T>,
    private val publishers: List<Publisher<T>>
) :
    AbstractProcessor<T, List<T?>>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in List<T?>>): ProcessorSubscription<T, List<T?>> {
        return CombineProcessorProcessorSubscription(subscriber, publishers)
    }

    class CombineProcessorProcessorSubscription<T>(
        private val subscriber: Subscriber<in List<T?>>,
        private val publishers: List<Publisher<T>>
    ) : ProcessorSubscription<T, List<T?>>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()
        private val publishersResult = AtomicListReference<PublisherResult<T>>()
        private val parentPublisherResultIndex = 0

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancel()
        }

        override fun onNext(t: T, subscriber: Subscriber<in List<T?>>) {
            val cancellableManager = cancellableManagerProvider.cancelPreviousAndCreate()
            publishersResult.removeAll(publishersResult.value)

            publishersResult.add(PublisherResult<T>().also { it.value = t })
            repeat(publishers.size) {
                publishersResult.add(PublisherResult())
            }

            publishers.forEachIndexed { index, publisher ->
                val publisherResultIndex = index + 1
                publisher.subscribe(cancellableManager,
                    onNext = { updatePublisherResultValue(publisherResultIndex, it) },
                    onError = { onError(it) },
                    onCompleted = { markPublisherResultCompleted(publisherResultIndex) }
                )
            }

            if (publishers.isEmpty()) {
                dispatchResultIfNeeded()
            }
        }

        override fun onError(t: Throwable) {
            super.onError(t)
            cancellableManagerProvider.cancelPreviousAndCreate()
            cancelActiveSubscription()
        }

        private fun markPublisherResultCompleted(publisherResultIndex: Int) {
            publishersResult.value[publisherResultIndex].completed = true
            dispatchResultIfNeeded(true)
        }

        private fun updatePublisherResultValue(publisherResultIndex: Int, value: T) {
            publishersResult.value[publisherResultIndex].value = value
            dispatchResultIfNeeded()
        }

        override fun onComplete() {
            markPublisherResultCompleted(parentPublisherResultIndex)
        }

        private fun dispatchResultIfNeeded(forPublisherCompletion: Boolean = false) {
            var allValues = true
            var allCompleted = true
            publishersResult.value.forEach publishersLoop@{
                allValues = allValues && it.value != null
                allCompleted = allCompleted && it.completed
                if (!allValues && !allCompleted) {
                    return@publishersLoop
                }
            }

            if (allValues && !forPublisherCompletion) {
                dispatchResult()
            } else if (allCompleted && !allValues) {
                dispatchResult()
            }

            if (allCompleted) {
                subscriber.onComplete()
            }
        }

        private fun dispatchResult() {
            subscriber.onNext(publishersResult.value.map { it.value })
        }
    }
}

class PublisherResult<T> {
    private val internalValue = AtomicReference<T?>(null)
    private val internalCompleted = AtomicReference(false)
    var value: T?
        get() = internalValue.value
        set(value) {
            internalValue.setOrThrow(internalValue.value, value)
        }
    var completed: Boolean
        get() = internalCompleted.value
        set(value) {
            internalCompleted.setOrThrow(false, value)
        }
}
