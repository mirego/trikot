package com.mirego.trikot.streams.reactive

open class BehaviorSubjectImpl<T>(
    initialValue: T? = null
) : PublishSubjectImpl<T>(), BehaviorSubject<T> {

    init {
        this.value = initialValue
    }

    override fun addSubscription(subscription: PublisherSubscription<T>) {
        super.addSubscription(subscription)
        if (!subscription.isCancelled) {
            this.value?.let { subscription.dispatchValue(it) }
            this.error?.let { subscription.dispatchError(it) }
            if (this.completed) { subscription.dispatchCompleted() }
        }
    }
}
