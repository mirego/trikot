package com.mirego.trikot.foundation.timers

import kotlin.time.Duration
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.time.ExperimentalTime
import java.util.Timer

@ExperimentalTime
actual class PlatformTimer actual constructor(delay: Duration, repeat: Boolean, block: () -> Unit) :
    com.mirego.trikot.foundation.timers.Timer {
    private val timer = if (repeat)
        Timer(true).scheduleAtFixedRate(delay.toLongMilliseconds(), Long.MAX_VALUE) { block() }
    else
        Timer(true).schedule(delay.toLongMilliseconds()) { block() }

    actual override fun cancel() {
        timer.cancel()
    }
}
