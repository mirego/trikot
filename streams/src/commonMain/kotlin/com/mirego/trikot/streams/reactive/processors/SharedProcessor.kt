package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import org.reactivestreams.Processor
import org.reactivestreams.Publisher
import org.reactivestreams.Subscription

class SharedProcessor<T>(private val parentPublisher: Publisher<T>) : BehaviorSubjectImpl<T>(), Processor<T, T> {
    private val cancellableManagerProvider = CancellableManagerProvider()
    private val subscriptionCancellableManager = AtomicReference(CancellableManager())

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        if (!completed) {
            subscriptionCancellableManager.setOrThrow(subscriptionCancellableManager.value, cancellableManagerProvider.cancelPreviousAndCreate())
            parentPublisher.subscribe(this)
        }
    }

    override fun onNoSubscription() {
        super.onNoSubscription()
        if (!completed) {
            cleanupValues()
        }
        cancellableManagerProvider.cancelPreviousAndCreate()
    }

    override fun onSubscribe(s: Subscription) = with(subscriptionCancellableManager.value) { add { s.cancel() } }

    override fun onNext(t: T) = let { value = t }

    override fun onError(t: Throwable) = let { error = t }

    override fun onComplete() {
        cancellableManagerProvider.cancelPreviousAndCreate()
        complete()
    }
}
