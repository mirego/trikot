package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.timers.TimerFactory
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import kotlin.time.Duration

class RepeatablePublisher(
    private val delay: Duration,
    private val timerFactory: TimerFactory
) : BehaviorSubjectImpl<Int>(0) {

    private val cancellableManagerProvider = CancellableManagerProvider()

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        val cancellableManager = cancellableManagerProvider.cancelPreviousAndCreate()

        val timer = timerFactory.repeatable(delay) { value = (value ?: 0) + 1 }

        cancellableManager.add { timer.cancel() }
    }

    override fun onNoSubscription() {
        super.onNoSubscription()
        cancellableManagerProvider.cancelPreviousAndCreate()
    }
}
