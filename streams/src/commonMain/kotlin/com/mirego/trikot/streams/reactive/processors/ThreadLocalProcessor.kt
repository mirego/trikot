package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.MrFreeze
import com.mirego.trikot.foundation.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.observeOn
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.reactive.subscribeOn
import org.reactivestreams.Processor
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.native.concurrent.ThreadLocal

class ThreadLocalProcessor<T>(
    private val parentPublisher: Publisher<T>,
    private val observeOnQueue: DispatchQueue,
    private val subscribeOnQueue: DispatchQueue
) : Processor<T, T> {
    override fun onSubscribe(s: Subscription) {
        throw IllegalStateException("Should never be called")
    }

    override fun onNext(t: T) {
        throw IllegalStateException("Should never be called")
    }

    override fun onError(t: Throwable) {
        throw IllegalStateException("Should never be called")
    }

    override fun onComplete() {
        throw IllegalStateException("Should never be called")
    }

    override fun subscribe(s: Subscriber<in T>) {
        s.onSubscribe(ThreadLocalSubscription(s, parentPublisher, subscribeOnQueue, observeOnQueue))
    }

    class ThreadLocalSubscription<T>(
        subscriber: Subscriber<in T>,
        parentPublisher: Publisher<T>,
        subscriptionQueue: DispatchQueue,
        private val observeOnQueue: DispatchQueue
    ) : Subscription {

        private val cancellableManager = CancellableManager()

        override fun request(n: Long) {
            // NO-OP
        }

        override fun cancel() {
            cancellableManager.cancel()
            observeOnQueue.dispatch { ThreadLocalProcessorSubscribers.remove(this) }
        }

        init {
            MrFreeze.ensureNeverFrozen(subscriber)
            ThreadLocalProcessorSubscribers.set(this, subscriber)
            val subscription = this
            parentPublisher.subscribeOn(subscriptionQueue)
                .observeOn(observeOnQueue)
                .subscribe(cancellableManager,
                    onNext = {
                        ThreadLocalProcessorSubscribers.get(subscription)?.onNext(it)
                    },
                    onError = {
                        ThreadLocalProcessorSubscribers.get(subscription)?.onError(it)
                    },
                    onCompleted = {
                        ThreadLocalProcessorSubscribers.get(subscription)?.onComplete()
                    }
                )
        }
    }
}

@ThreadLocal
object ThreadLocalProcessorSubscribers {
    fun <T> get(subscription: ThreadLocalProcessor.ThreadLocalSubscription<T>): Subscriber<T>? {
        @Suppress("UNCHECKED_CAST")
        return subscriptions[subscription] as? Subscriber<T>
    }

    fun <T> set(
        subscription: ThreadLocalProcessor.ThreadLocalSubscription<T>,
        s: Subscriber<in T>
    ) {
        subscriptions[subscription] = s
    }

    fun <T> remove(subscription: ThreadLocalProcessor.ThreadLocalSubscription<T>) {
        subscriptions.remove(subscription)
    }

    private val subscriptions =
        HashMap<ThreadLocalProcessor.ThreadLocalSubscription<*>, Subscriber<*>>().also {
            MrFreeze.ensureNeverFrozen(it)
        }
}
