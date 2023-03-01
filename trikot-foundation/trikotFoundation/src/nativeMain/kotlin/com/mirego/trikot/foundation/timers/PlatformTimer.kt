package com.mirego.trikot.foundation.timers

import platform.Foundation.NSDefaultRunLoopMode
import platform.Foundation.NSRunLoop
import platform.Foundation.NSTimer
import kotlin.time.Duration

actual class PlatformTimer actual constructor(delay: Duration, repeat: Boolean, block: () -> Unit) : Timer {
    private val timerBlock: (NSTimer?) -> Unit = { _ -> block() }
    private val timer = NSTimer.timerWithTimeInterval(
        (delay.inWholeMilliseconds / 1000.0),
        repeat,
        timerBlock
    )

    init {
        NSRunLoop.mainRunLoop.addTimer(timer, NSDefaultRunLoopMode)
    }

    actual override fun cancel() {
        timer.invalidate()
    }
}
