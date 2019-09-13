package com.mirego.trikot.foundation.timers

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import platform.Foundation.NSTimer
import platform.Foundation.NSRunLoop
import platform.Foundation.NSDefaultRunLoopMode

@ExperimentalTime
actual class PlatformTimer actual constructor(delay: Duration, repeat: Boolean, block: () -> Unit) : Timer {
    private val timer: NSTimer
    init {
        timer = NSTimer.timerWithTimeInterval((delay.toLongMilliseconds() / 1000.0), repeat) {
            block()
        }
        NSRunLoop.currentRunLoop.addTimer(timer, NSDefaultRunLoopMode)
    }

    actual override fun cancel() {
        timer.invalidate()
    }
}
