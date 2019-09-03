package com.mirego.trikot.foundation.timers

import com.mirego.trikot.foundation.concurrent.duration.Duration

interface TimerFactory {
    fun single(delay: Duration, block: () -> Unit): Timer
    fun repeatable(delay: Duration, block: () -> Unit): Timer
}
