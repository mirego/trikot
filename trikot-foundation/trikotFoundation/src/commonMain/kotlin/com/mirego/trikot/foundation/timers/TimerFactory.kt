package com.mirego.trikot.foundation.timers

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
interface TimerFactory {
    fun single(delay: Duration, block: () -> Unit): Timer
    fun repeatable(delay: Duration, block: () -> Unit): Timer
}
