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

typealias OnErrorResumeNextBlock<T> = (Throwable) -> Publisher<T>

class OnErrorResumeNextProcessor<T>(parentPublisher: Publisher<T>, private var block: OnErrorResumeNextBlock<T>) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return OnErrorResumeNextProcessorSubscription(subscriber, block)
    }

    class OnErrorResumeNextProcessorSubscription<T>(
        private val subscriber: Subscriber<in T>,
        private val block: OnErrorResumeNextBlock<T>
    ) : ProcessorSubscription<T, T>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()
        private val onErrorOnce = AtomicReference(false)
        private val serialQueue = SynchronousSerialQueue()

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancel()
        }

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            subscriber.onNext(t)
        }

        override fun onError(t: Throwable) {
            if (!onErrorOnce.compareAndSet(false, true)) {
                throw IllegalStateException("onError was already called once")
            }

            val newPublisher = try {
                block(t)
            } catch (e: StreamsProcessorException) {
                subscriber.onError(e)
                return
            }

            newPublisher.observeOn(serialQueue).subscribe(
                cancellableManagerProvider.cancelPreviousAndCreate(),
                onNext = { subscriber.onNext(it) },
                onError = { subscriber.onError(it) },
                onCompleted = { subscriber.onComplete() }
            )
        }
    }
}
