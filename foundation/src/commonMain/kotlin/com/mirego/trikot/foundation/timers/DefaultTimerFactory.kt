package com.mirego.trikot.foundation.timers

import com.mirego.trikot.foundation.concurrent.duration.Duration

class DefaultTimerFactory: TimerFactory {
    override fun timer(delay: Duration, repeat: Boolean, block: () -> Unit): Timer {
        return PlatformTimer(delay, repeat, block)
    }
}
