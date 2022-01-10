package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue

open class BehaviorSubjectImpl<T>(
    initialValue: T? = null,
    serialQueue: SynchronousSerialQueue = SynchronousSerialQueue()
) : PublishSubjectImpl<T>(serialQueue), BehaviorSubject<T> {

    init {
        this.value = initialValue
    }

    override fun onNewSubscription(subscription: PublisherSubscription<T>) {
        super.onNewSubscription(subscription)
        this.value?.let { subscription.dispatchValue(it) }
        this.error?.let { subscription.dispatchError(it) }
    }
}
