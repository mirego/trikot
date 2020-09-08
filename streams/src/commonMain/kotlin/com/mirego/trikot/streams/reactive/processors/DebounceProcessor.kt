package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.FoundationConfiguration
import com.mirego.trikot.foundation.timers.TimerFactory
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

@ExperimentalTime
class DebounceProcessor<T>(
    parentPublisher: Publisher<T>,
    private val timeout: Duration,
    private val timerFactory: TimerFactory = FoundationConfiguration.timerFactory
) :
    AbstractProcessor<T, T>(parentPublisher) {
    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return DebounceProcessorSubscription(subscriber, timeout, timerFactory)
    }

    class DebounceProcessorSubscription<T>(
        subscriber: Subscriber<in T>,
        private val timeout: Duration,
        private val timerFactory: TimerFactory = FoundationConfiguration.timerFactory
    ) : ProcessorSubscription<T, T>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            cancellableManagerProvider.cancelPreviousAndCreate().also { cancellableManager ->
                timerFactory.single(timeout) { subscriber.onNext(t) }.also {
                    cancellableManager.add { it.cancel() }
                }
            }
        }

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancelPreviousAndCreate()
        }
    }
}
