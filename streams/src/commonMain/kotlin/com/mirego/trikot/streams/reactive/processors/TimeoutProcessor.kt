package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.FoundationConfiguration
import com.mirego.trikot.foundation.timers.TimerFactory
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.StreamsTimeoutException
import kotlin.time.Duration
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class TimeoutProcessor<T>(
    private val duration: Duration,
    private val timerFactory: TimerFactory = FoundationConfiguration.timerFactory,
    private val timeoutMessage: String,
    parentPublisher: Publisher<T>
) :
    AbstractProcessor<T, T>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in T>): ProcessorSubscription<T, T> {
        return TimeoutProcessorSubscription(duration, timerFactory, timeoutMessage, subscriber)
    }

    class TimeoutProcessorSubscription<T>(
        private val duration: Duration,
        private val timerFactory: TimerFactory,
        private val timeoutMessage: String,
        subscriber: Subscriber<in T>
    ) : ProcessorSubscription<T, T>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()

        init {
            resetTimer()
        }

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            cancellableManagerProvider.cancel()
        }

        private fun resetTimer() {
            val cancellableManager = cancellableManagerProvider.cancelPreviousAndCreate()
            val timer = timerFactory.single(duration) {
                onError(StreamsTimeoutException(timeoutMessage))
            }
            cancellableManager.add { timer.cancel() }
        }

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            cancellableManagerProvider.cancelPreviousAndCreate()
            subscriber.onNext(t)
            resetTimer()
        }

        override fun onError(t: Throwable) {
            cancellableManagerProvider.cancelPreviousAndCreate()
            cancelActiveSubscription()
            super.onError(t)
        }

        override fun onComplete() {
            cancellableManagerProvider.cancelPreviousAndCreate()
            super.onComplete()
        }
    }
}
