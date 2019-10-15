package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.concurrent.AtomicListReference
import com.mirego.trikot.foundation.concurrent.AtomicReference
import org.reactivestreams.Subscriber

open class PublishSubjectImpl<T> : PublishSubject<T> {
    private val subscriptions = AtomicListReference<PublisherSubscription<T>>()
    private val atomicValue = AtomicReference<T?>(null)
    private val atomicError = AtomicReference<Throwable?>(null)
    private val isCompleted = AtomicReference(false)
    protected val hasSubscriptions
        get() = subscriptions.value.count() > 0

    protected val onPublisherSubscriptionCancelled: OnPublisherSubscriptionCancelled<T> = { publisherSubscription ->
        removeSubscription(publisherSubscription)
    }

    protected val completed get() = isCompleted.value

    override var value: T?
        get() {
            return atomicValue.value
        }
        set(value) {
            atomicValue.setOrThrow(atomicValue.value, value)

            value?.let {
                error?.let {
                    throw IllegalStateException("Value should not be set after an error.")
                }
                if (isCompleted.value) {
                    throw IllegalStateException("Value should not be set after publisher has completed.")
                }
                dispatchValueToSubscribers(it)
            }
        }

    override var error: Throwable?
        get() {
            return atomicError.value
        }
        set(error) {
            value = null
            atomicError.setOrThrow(atomicError.value, error)
            error?.let {
                dispatchErrorToSubscribers(it)
            }
        }

    override fun subscribe(s: Subscriber<in T>) {
        val subscription = PublisherSubscription(s, onPublisherSubscriptionCancelled)
        s.onSubscribe(subscription)
        addSubscription(subscription)
    }

    private fun removeSubscription(publisherSubscription: PublisherSubscription<T>) {
        if (subscriptions.value.contains(publisherSubscription) && subscriptions.remove(publisherSubscription).isEmpty()) {
            onNoSubscription()
        }
    }

    private fun addSubscription(subscription: PublisherSubscription<T>) {
        if (!subscription.isCancelled) {
            onNewSubscription(subscription)
            if (!subscription.isCancelled) {
                if (this.completed) {
                    subscription.dispatchCompleted()
                } else if (subscriptions.add(subscription).count() == 1) {
                    onFirstSubscription()
                }
            }
        }
    }

    protected open fun onNewSubscription(subscription: PublisherSubscription<T>) {
    }

    protected open fun dispatchValueToSubscribers(value: T) {
        subscriptions.value.forEach { it.dispatchValue(value) }
    }

    protected open fun dispatchErrorToSubscribers(error: Throwable) {
        subscriptions.value.forEach { it.dispatchError(error) }
    }

    override fun complete() {
        isCompleted.setOrThrow(false, true)
        subscriptions.value.forEach { it.dispatchCompleted() }
    }

    protected open fun onFirstSubscription() {
    }

    protected open fun onNoSubscription() {
    }
}
