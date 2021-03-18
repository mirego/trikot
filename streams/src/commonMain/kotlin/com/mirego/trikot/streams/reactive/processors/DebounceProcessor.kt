package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.FoundationConfiguration
import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import com.mirego.trikot.foundation.concurrent.setOrThrow
import com.mirego.trikot.foundation.timers.TimerFactory
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

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
        private val subscriber: Subscriber<in T>,
        private val timeout: Duration,
        private val timerFactory: TimerFactory = FoundationConfiguration.timerFactory
    ) : ProcessorSubscription<T, T>(subscriber) {
        private val cancellableManagerProvider = CancellableManagerProvider()
        private val valueToDispatch = AtomicReference<T?>(null)
        private val isCancelledOrCompleted = AtomicReference<Boolean>(false)
        private val serialQueue = SynchronousSerialQueue()

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            cancellableManagerProvider.cancelPreviousAndCreate().also { cancellableManager ->
                valueToDispatch.setOrThrow(t)
                timerFactory.single(timeout) {
                    serialQueue.dispatch {
                        if (!isCancelledOrCompleted.value) {
                            valueToDispatch.compareAndSet(t, null)
                            subscriber.onNext(t)
                        }
                    }
                }.also {
                    cancellableManager.add { it.cancel() }
                }
            }
        }

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            isCancelledOrCompleted.setOrThrow(true)
            cancellableManagerProvider.cancel()
        }

        override fun onError(t: Throwable) {
            isCancelledOrCompleted.setOrThrow(true)
            cancellableManagerProvider.cancel()
            serialQueue.dispatch {
                super.onError(t)
            }
        }

        override fun onComplete() {
            isCancelledOrCompleted.setOrThrow(true)
            serialQueue.dispatch {
                valueToDispatch.value?.let {
                    subscriber.onNext(it)
                }
                super.onComplete()
                cancellableManagerProvider.cancel()
            }
        }
    }
}
