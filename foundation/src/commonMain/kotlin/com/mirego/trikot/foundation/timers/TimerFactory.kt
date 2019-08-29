package com.mirego.trikot.foundation.timers

import com.mirego.trikot.foundation.concurrent.duration.Duration
import com.mirego.trikot.foundation.timers.Timer

interface TimerFactory {
    fun timer(delay: Duration, repeat: Boolean, block: () -> Unit): Timer
}
