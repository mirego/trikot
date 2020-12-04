package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.processors.AbstractProcessor
import com.mirego.trikot.streams.reactive.processors.ProcessorSubscription
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import kotlin.test.Test
import kotlin.test.assertEquals

class SubscriberFromBlockTests {
    var behaviorSubject = Publishers.behaviorSubject<String>()

    @Test
    fun whenCancelledCallbackIsNotCalledTwice() {
        behaviorSubject.value = "1"

        var count = 0
        val cancellableManager = CancellableManager()

        val initial = TriggerHappyProcessor(behaviorSubject, "0")
        val next = TriggerHappyProcessor(initial, "2")

        next.subscribe(cancellableManager) {
            cancellableManager.cancel()
            val oldValue = behaviorSubject.value
            behaviorSubject.value = "${(oldValue?.toInt() ?: 0) + 1}"
            count++
        }

        assertEquals(1, count)
    }

    class TriggerHappyProcessor<T>(parentPublisher: Publisher<T>, private val value: T) :
        AbstractProcessor<T, T>(parentPublisher) {

        override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
            subscriber.onNext(value)
            return TriggerHappyProcessorSubscription(subscriber)
        }

        class TriggerHappyProcessorSubscription<T>(
            subscriber: Subscriber<in T>
        ) : ProcessorSubscription<T, T>(subscriber) {
            override fun onNext(t: T, subscriber: Subscriber<in T>) {
                subscriber.onNext(t)
            }
        }
    }
}
