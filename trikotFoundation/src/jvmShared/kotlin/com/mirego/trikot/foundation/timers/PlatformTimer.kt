package com.mirego.trikot.foundation.timers

import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
actual class PlatformTimer actual constructor(delay: Duration, repeat: Boolean, block: () -> Unit) :
    com.mirego.trikot.foundation.timers.Timer {
    private val timer = if (repeat)
        Timer(true).scheduleAtFixedRate(delay.toLongMilliseconds(), delay.toLongMilliseconds()) { block() }
    else
        Timer(true).schedule(delay.toLongMilliseconds()) { block() }

    actual override fun cancel() {
        timer.cancel()
    }
}
