package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.concurrent.AtomicReference
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

typealias OnPublisherSubscriptionCancelled<T> = (PublisherSubscription<T>) -> Unit

open class PublisherSubscription<T>(
    private val subscriber: Subscriber<in T>,
    private val onCancel: OnPublisherSubscriptionCancelled<T>
) : Subscription {

    private val privateIsCancelled = AtomicReference(false)

    val isCancelled get() = privateIsCancelled.value

    override fun cancel() {
        privateIsCancelled.setOrThrow(privateIsCancelled.value, true)
        onCancel(this)
    }

    fun dispatchValue(value: T) {
        subscriber.onNext(value)
    }

    fun dispatchError(error: Throwable) {
        cancel()
        subscriber.onError(error)
    }

    fun dispatchCompleted() {
        cancel()
        subscriber.onComplete()
    }

    override fun request(n: Long) {
    }
}
