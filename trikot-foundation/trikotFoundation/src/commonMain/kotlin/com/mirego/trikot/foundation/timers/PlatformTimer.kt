package com.mirego.trikot.foundation.timers

import kotlin.time.Duration

expect class PlatformTimer constructor(delay: Duration, repeat: Boolean, block: () -> Unit) : Timer {
    override fun cancel()
}
