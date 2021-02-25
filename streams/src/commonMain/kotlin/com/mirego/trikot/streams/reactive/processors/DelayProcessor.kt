package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.timers.TimerFactory
import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class DelayProcessor<T>(
    parentPublisher: Publisher<T>,
    private val duration: Duration,
    private val timerFactory: TimerFactory
) : AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return DelayProcessorSubscription(subscriber, duration, timerFactory)
    }

    class DelayProcessorSubscription<T>(
        subscriber: Subscriber<in T>,
        private val delay: Duration,
        private val timerFactory: TimerFactory
    ) : ProcessorSubscription<T, T>(subscriber) {

        private val cancellableManagerProvider = CancellableManager()

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            val timer = timerFactory.single(delay) { subscriber.onNext(t) }
            cancellableManagerProvider.add { timer.cancel() }
        }

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancel()
        }

        override fun onComplete() {
            super.onComplete()
            cancellableManagerProvider.cancel()
        }
    }
}
