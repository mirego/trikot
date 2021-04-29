package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.foundation.concurrent.setOrThrow
import com.mirego.trikot.foundation.timers.Timer
import com.mirego.trikot.foundation.timers.TimerFactory
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.time.Duration

class TimerPublisher(
    private val delay: Duration,
    private val timerFactory: TimerFactory
) : Publisher<Long> {
    override fun subscribe(s: Subscriber<in Long>) {
        val timerSubscription = TimerSubscription(s)
        s.onSubscribe(timerSubscription)
        val timer = timerFactory.single(delay, timerSubscription::timerReached)
        timerSubscription.timerRef.setOrThrow(timer)
    }

    private class TimerSubscription(
        private val downstream: Subscriber<in Long>
    ) : Subscription {

        val timerRef = AtomicReference<Timer?>(null)
        private var cancelled by atomic(false)

        fun timerReached() {
            if (!cancelled) {
                downstream.onNext(0L)
                timerRef.compareAndSet(timerRef.value, null)
                downstream.onComplete()
            }
        }

        override fun request(n: Long) {
            // NO-OP
        }

        override fun cancel() {
            val timer = timerRef.value
            if (timer != null && timerRef.compareAndSet(timer, null)) {
                timer.cancel()
                cancelled = true
            }
        }
    }
}
