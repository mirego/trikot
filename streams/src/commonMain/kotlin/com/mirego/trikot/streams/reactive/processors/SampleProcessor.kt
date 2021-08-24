package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.FoundationConfiguration
import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import com.mirego.trikot.foundation.timers.TimerFactory
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.time.Duration

class SampleProcessor<T>(
    parentPublisher: Publisher<T>,
    private val interval: Duration,
    private val timerFactory: TimerFactory = FoundationConfiguration.timerFactory
) : AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return SampleProcessorSubscription(subscriber, interval, timerFactory)
    }

    class SampleProcessorSubscription<T>(
        private val subscriber: Subscriber<in T>,
        private val interval: Duration,
        private val timerFactory: TimerFactory
    ) : ProcessorSubscription<T, T>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()
        private val lastEmittedValue = AtomicReference<T?>(null)
        private val synchronousSerialQueue = SynchronousSerialQueue()

        private fun initializeTimer() {
            val cancellableManager = cancellableManagerProvider.cancelPreviousAndCreate()

            val recurringTimer = timerFactory.repeatable(interval) {
                synchronousSerialQueue.dispatch {
                    dispatchSampledValue()
                }
            }

            cancellableManager.add { recurringTimer.cancel() }
        }

        override fun onSubscribe(s: Subscription) {
            super.onSubscribe(s)
            initializeTimer()
        }

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            synchronousSerialQueue.dispatch {
                lastEmittedValue.setOrThrow(lastEmittedValue.value, t)
            }
        }

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancelPreviousAndCreate()
        }

        override fun onComplete() {
            dispatchSampledValue()
            super.onComplete()
        }

        private fun dispatchSampledValue() {
            lastEmittedValue.getAndSet(null)?.let { emittedValue ->
                subscriber.onNext(emittedValue)
            }
        }
    }
}

private fun <T> AtomicReference<T?>.getAndSet(value: T?): T? {
    val referenceValue = this.value

    setOrThrow(referenceValue, value)

    return referenceValue
}
