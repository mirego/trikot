package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

typealias ScanProcessorBlock<T> = (acc: T, current: T) -> T

class ScanProcessor<T>(parentPublisher: Publisher<T>, private val initialValue: T?, private val block: ScanProcessorBlock<T>) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return ScanProcessorSubscription(subscriber, initialValue, block)
    }

    class ScanProcessorSubscription<T>(
        subscriber: Subscriber<in T>,
        initialValue: T?,
        private val block: ScanProcessorBlock<T>
    ) : ProcessorSubscription<T, T>(subscriber) {

        private val atomicValue = AtomicReference<T?>(initialValue)

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            val v = atomicValue.value
            if (v == null) {
                atomicValue.setOrThrow(null, t)
                subscriber.onNext(t)
            } else {
                val result = try {
                    block(v, t)
                } catch (exception: StreamsProcessorException) {
                    onError(exception)
                    return
                }
                atomicValue.setOrThrow(v, result)
                subscriber.onNext(result)
            }
        }
    }
}
