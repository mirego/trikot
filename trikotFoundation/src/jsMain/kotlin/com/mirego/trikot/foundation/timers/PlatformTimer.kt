package com.mirego.trikot.foundation.timers

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlinx.browser.window

@ExperimentalTime
actual class PlatformTimer actual constructor(delay: Duration, private val repeat: Boolean, block: () -> Unit) :
    Timer {
    private val timeoutId = if (repeat) {
        window.setInterval({ block() }, delay.toLongMilliseconds().toInt())
    } else {
        window.setTimeout({ block() }, delay.toLongMilliseconds().toInt())
    }

    actual override fun cancel() {
        if (repeat) window.clearInterval(timeoutId) else window.clearTimeout(timeoutId)
    }
}
