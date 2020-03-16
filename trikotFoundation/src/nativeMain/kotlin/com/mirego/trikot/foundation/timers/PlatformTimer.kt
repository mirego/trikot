package com.mirego.trikot.foundation.timers

import com.mirego.trikot.foundation.concurrent.freeze
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import platform.Foundation.NSTimer
import platform.Foundation.NSRunLoop
import platform.Foundation.NSDefaultRunLoopMode

@ExperimentalTime
actual class PlatformTimer actual constructor(delay: Duration, repeat: Boolean, block: () -> Unit) : Timer {
    private val timerBlock: (NSTimer?) -> Unit = { _ -> block() }
    private val frozenTimerBlock = timerBlock.also { freeze(it) }
    private val timer = NSTimer.timerWithTimeInterval((delay.toLongMilliseconds() / 1000.0), repeat, frozenTimerBlock)
            init {
        NSRunLoop.mainRunLoop.addTimer(timer, NSDefaultRunLoopMode)
    }

    actual override fun cancel() {
        timer.invalidate()
    }
}
