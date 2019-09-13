package com.mirego.trikot.foundation.timers

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

expect class PlatformTimer @ExperimentalTime constructor(delay: Duration, repeat: Boolean, block: () -> Unit) : Timer {
    override fun cancel()
}
