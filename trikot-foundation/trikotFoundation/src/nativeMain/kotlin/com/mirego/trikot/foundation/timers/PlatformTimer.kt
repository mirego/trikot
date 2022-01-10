package com.mirego.trikot.foundation.timers

import com.mirego.trikot.foundation.concurrent.freeze
import platform.Foundation.NSDefaultRunLoopMode
import platform.Foundation.NSRunLoop
import platform.Foundation.NSTimer
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
actual class PlatformTimer actual constructor(delay: Duration, repeat: Boolean, block: () -> Unit) : Timer {
    private val timerBlock: (NSTimer?) -> Unit = { _ -> block() }
    private val frozenTimerBlock = timerBlock.also { freeze(it) }
    private val timer = NSTimer.timerWithTimeInterval(
        (delay.inWholeMilliseconds / 1000.0),
        repeat,
        frozenTimerBlock
    )

    init {
        NSRunLoop.mainRunLoop.addTimer(timer, NSDefaultRunLoopMode)
    }

    actual override fun cancel() {
        timer.invalidate()
    }
}
