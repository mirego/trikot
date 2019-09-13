package com.mirego.trikot.foundation.timers

import kotlin.time.Duration
import kotlin.concurrent.schedule
import kotlin.time.ExperimentalTime

@ExperimentalTime
actual class PlatformTimer actual constructor(delay: Duration, repeat: Boolean, block: () -> Unit) :
    Timer {
    val timer = java.util.Timer(repeat).schedule(delay.toLongMilliseconds()) {
        block()
    }

    actual override fun cancel() {
        timer.cancel()
    }
}
