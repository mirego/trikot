package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.PublishSubject
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.observeOn
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

typealias RetryWhenPublisherBlock = (Publisher<out Throwable>) -> Publisher<out Any>

class RetryWhenProcessor<T>(
    parentPublisher: Publisher<T>,
    private val block: RetryWhenPublisherBlock
) : AbstractProcessor<T, T>(parentPublisher) {
    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return RetryWhenSubscription(subscriber, parentPublisher, block)
    }
}

class RetryWhenSubscription<T>(
    private val subscriber: Subscriber<in T>,
    private val parentPublisher: Publisher<T>,
    block: RetryWhenPublisherBlock
) : ProcessorSubscription<T, T>(subscriber) {

    private val errorPublisher: PublishSubject<Throwable> = Publishers.publishSubject()
    private val serialQueue = SynchronousSerialQueue()
    private val cancellableManagerProvider = CancellableManagerProvider()
    private val resubscribing = AtomicReference(false)

    init {
        val whenPublisher = block.invoke(errorPublisher)
        whenPublisher.observeOn(serialQueue)
            .subscribe(cancellableManagerProvider.cancelPreviousAndCreate(),
                onNext = {
                    resubscribe()
                },
                onError = {
                    subscriber.onError(it)
                },
                onCompleted = {
                    subscriber.onComplete()
                })
    }

    override fun onNext(t: T, subscriber: Subscriber<in T>) {
        subscriber.onNext(t)
    }

    override fun onCancel(s: Subscription) {
        if (!resubscribing.value) {
            cancellableManagerProvider.cancelPreviousAndCreate()
            super.onCancel(s)
        }
    }

    override fun onError(t: Throwable) {
        if (!resubscribing.value) {
            errorPublisher.value = t
        }
    }

    private fun resubscribe() {
        serialQueue.dispatch {
            resubscribing.setOrThrow(resubscribing.value, true)
            cancelActiveSubscription()
            parentPublisher.subscribe(this)
            resubscribing.setOrThrow(resubscribing.value, false)
        }
    }
}
