package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class SubscriberFromBlock<T>(
    private val cancelableManager: CancellableManager,
    private val subscriptionBlock: SubscriptionBlock<T>,
    private val subscriptionErrorBlock: SubscriptionErrorBlock?,
    private val onCompleted: SubscriptionCompletedBlock?
) : Subscriber<T> {
    override fun onSubscribe(s: Subscription) {
        cancelableManager.add(object : Cancellable {
            override fun cancel() {
                s.cancel()
            }
        })
    }

    override fun onNext(t: T) {
        subscriptionBlock(t)
    }

    override fun onError(t: Throwable) {
        subscriptionErrorBlock?.let { it(t) }
    }

    override fun onComplete() {
        onCompleted?.let { it() }
    }
}
