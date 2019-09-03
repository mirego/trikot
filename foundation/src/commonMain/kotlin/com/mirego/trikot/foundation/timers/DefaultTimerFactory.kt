package com.mirego.trikot.foundation.timers

import com.mirego.trikot.foundation.concurrent.duration.Duration

class DefaultTimerFactory : TimerFactory {
    override fun single(delay: Duration, block: () -> Unit): Timer {
        return PlatformTimer(delay, false, block)
    }

    override fun repeatable(delay: Duration, block: () -> Unit): Timer {
        return PlatformTimer(delay, true, block)
    }
}
