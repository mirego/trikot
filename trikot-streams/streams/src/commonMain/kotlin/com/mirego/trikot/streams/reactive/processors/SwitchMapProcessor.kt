package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import com.mirego.trikot.streams.reactive.observeOn
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
        private val subscriber: Subscriber<in R>,
        private val block: SwitchMapProcessorBlock<T, R>
    ) : ProcessorSubscription<T, R>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()
        private val isCompleted = AtomicReference<Boolean>(false)
        private val isChildCompleted = AtomicReference<Boolean>(false)
        private val currentPublisher = AtomicReference<Publisher<R>?>(null)
        private val onNextValidation = AtomicReference(0)
        private val serialQueue = SynchronousSerialQueue()

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancel()
        }

        override fun onComplete() {
            serialQueue.dispatch {
                isCompleted.setOrThrow(false, true)
                dispatchCompletedIfNeeded()
            }
        }

        override fun onNext(t: T, subscriber: Subscriber<in R>) {
            onNextValidation.setOrThrow(0, 1)
            isChildCompleted.setOrThrow(isChildCompleted.value, false)

            val cancellableManager = cancellableManagerProvider.cancelPreviousAndCreate()

            val newPublisher = try {
                block(t)
            } catch (e: StreamsProcessorException) {
                onError(e)
                return
            }

            currentPublisher.setOrThrow(currentPublisher.value, newPublisher)
            newPublisher.observeOn(serialQueue).subscribe(
                cancellableManager,
                onNext = { subscriber.onNext(it) },
                onError = {
                    onError(it)
                },
                onCompleted = {
                    isChildCompleted.setOrThrow(isChildCompleted.value, true)
                    dispatchCompletedIfNeeded()
                }
            )

            onNextValidation.setOrThrow(1, 0)
        }

        override fun onError(t: Throwable) {
            super.onError(t)
            cancellableManagerProvider.cancelPreviousAndCreate()
            cancelActiveSubscription()
        }

        private fun dispatchCompletedIfNeeded() {
            if (isChildCompleted.value && isCompleted.value) {
                subscriber.onComplete()
            }
        }
    }
}
