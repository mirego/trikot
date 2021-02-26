package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.dispatchQueue.TrikotDispatchQueue
import com.mirego.trikot.foundation.concurrent.dispatchQueue.TrikotQueueDispatcher
import com.mirego.trikot.foundation.concurrent.dispatchQueue.dispatch
import com.mirego.trikot.foundation.concurrent.freeze
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class SubscribeOnProcessor<T>(parentPublisher: Publisher<T>, override val dispatchQueue: TrikotDispatchQueue) :
    AbstractProcessor<T, T>(parentPublisher = parentPublisher),
    TrikotQueueDispatcher {

    override fun subscribe(s: Subscriber<in T>) {
        freeze(s)
        dispatch {
            super.subscribe(s)
        }
    }

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return SubscribeOnProcessorSubscription(subscriber, dispatchQueue)
    }

    class SubscribeOnProcessorSubscription<T>(s: Subscriber<in T>, override val dispatchQueue: TrikotDispatchQueue) :
        ProcessorSubscription<T, T>(s),
        TrikotQueueDispatcher {
        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            subscriber.onNext(t)
        }

        override fun onCancel(s: Subscription) {
            freeze(s)
            dispatch {
                super.onCancel(s)
            }
        }
    }
}
