package com.mirego.trikot.foundation.timers

import com.mirego.trikot.foundation.concurrent.duration.Duration
import kotlin.concurrent.schedule

actual class PlatformTimer actual constructor(delay: Duration, repeat: Boolean, block: () -> Unit) :
    Timer {
    val timer = java.util.Timer(repeat).schedule(delay.milliseconds) {
        block()
    }

    actual override fun cancel() {
        timer.cancel()
    }
}
