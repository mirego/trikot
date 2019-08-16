package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.foundation.concurrent.dispatchQueue.QueueDispatcher
import com.mirego.trikot.foundation.concurrent.freeze
import com.mirego.trikot.foundation.concurrent.dispatchQueue.dispatch
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

open class ObserveOnProcessor<T>(parentPublisher: Publisher<T>, override val dispatchQueue: DispatchQueue) :
    AbstractProcessor<T, T>(parentPublisher = parentPublisher),
    QueueDispatcher {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return ObserveOnProcessorSubscription(subscriber, dispatchQueue)
    }

    class ObserveOnProcessorSubscription<T>(
        s: Subscriber<in T>,
        override val dispatchQueue: DispatchQueue
    ) : ProcessorSubscription<T, T>(s), QueueDispatcher {
        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            freeze(t)
            dispatch {
                subscriber.onNext(t)
            }
        }

        override fun onError(t: Throwable) {
            freeze(t)
            dispatch {
                super.onError(t)
            }
        }

        override fun onComplete() {
            dispatch {
                super.onComplete()
            }
        }
    }
}
