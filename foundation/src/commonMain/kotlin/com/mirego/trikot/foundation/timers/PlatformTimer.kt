package com.mirego.trikot.foundation.timers

import com.mirego.trikot.foundation.concurrent.duration.Duration

expect class PlatformTimer(delay: Duration, repeat: Boolean, block: () -> Unit) : Timer {
    override fun cancel()
}
