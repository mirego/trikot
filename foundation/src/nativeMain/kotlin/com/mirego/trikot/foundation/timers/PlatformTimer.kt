package com.mirego.trikot.foundation.timers

import com.mirego.trikot.foundation.concurrent.duration.Duration
import platform.Foundation.NSTimer

actual class PlatformTimer actual constructor(delay: Duration, repeat: Boolean, block: () -> Unit) : Timer {
    private val timer = NSTimer.scheduledTimerWithTimeInterval((delay.milliseconds / 1000).toDouble(), repeat) {
        block()
    }

    actual override fun cancel() {
        timer.invalidate()
    }
}
