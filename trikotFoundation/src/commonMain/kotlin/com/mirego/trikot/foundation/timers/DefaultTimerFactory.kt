package com.mirego.trikot.foundation.timers

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class DefaultTimerFactory : TimerFactory {
    override fun single(delay: Duration, block: () -> Unit): Timer {
        return PlatformTimer(delay, false, block)
    }

    override fun repeatable(delay: Duration, block: () -> Unit): Timer {
        return PlatformTimer(delay, true, block)
    }
}
