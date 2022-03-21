package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.dispatchQueue.SequentialDispatchQueue
import com.mirego.trikot.foundation.concurrent.dispatchQueue.TrikotDispatchQueue
import com.mirego.trikot.foundation.concurrent.dispatchQueue.TrikotQueueDispatcher
import com.mirego.trikot.foundation.concurrent.dispatchQueue.dispatch
import com.mirego.trikot.foundation.concurrent.freeze
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

open class ObserveOnProcessor<T>(
    parentPublisher: Publisher<T>,
    dispatchQueue: TrikotDispatchQueue
) : AbstractProcessor<T, T>(parentPublisher = parentPublisher) {

    private val dispatchQueue = when {
        dispatchQueue.isSerial() -> dispatchQueue
        else -> SequentialDispatchQueue(dispatchQueue)
    }

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return ObserveOnProcessorSubscription(subscriber, dispatchQueue)
    }

    class ObserveOnProcessorSubscription<T>(
        s: Subscriber<in T>,
        override val dispatchQueue: TrikotDispatchQueue
    ) : ProcessorSubscription<T, T>(s), TrikotQueueDispatcher {

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            freeze(t)
            dispatch { subscriber.onNext(t) }
        }

        override fun onError(t: Throwable) {
            freeze(t)
            dispatch { super.onError(t) }
        }

        override fun onComplete() {
            dispatch { super.onComplete() }
        }
    }
}
