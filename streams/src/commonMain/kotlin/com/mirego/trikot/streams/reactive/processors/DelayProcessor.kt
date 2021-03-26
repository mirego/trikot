package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
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

        private val serialQueue = SynchronousSerialQueue()
        private val cancellableManagerProvider = CancellableManager()

        private var blocksToRun: List<() -> Unit> by atomic(emptyList())

        private fun runNextBlock() {
            serialQueue.dispatch {
                blocksToRun.firstOrNull()?.invoke()
                blocksToRun = blocksToRun.drop(1)
            }
        }

        private fun scheduleTimerForNextBlock(delay: Duration) {
            timerFactory.single(delay, ::runNextBlock)
                .also { cancellableManagerProvider.add { it.cancel() } }
        }

        override fun onNext(t: T, subscriber: Subscriber<in T>) {
            blocksToRun = blocksToRun + { subscriber.onNext(t) }
            scheduleTimerForNextBlock(delay)
        }

        override fun onError(t: Throwable) {
            blocksToRun = blocksToRun + { super.onError(t) }
            scheduleTimerForNextBlock(delay)
        }

        override fun onComplete() {
            blocksToRun = blocksToRun + { super.onComplete() }
            scheduleTimerForNextBlock(delay)
        }

        override fun onCancel(s: Subscription) {
            super.onCancel(s)
            blocksToRun = emptyList()
            cancellableManagerProvider.cancel()
        }
    }
}
