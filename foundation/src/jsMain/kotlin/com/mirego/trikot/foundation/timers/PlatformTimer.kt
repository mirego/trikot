package com.mirego.trikot.foundation.timers

import com.mirego.trikot.foundation.concurrent.duration.Duration
import kotlin.browser.window

actual class PlatformTimer actual constructor(delay: Duration, private val repeat: Boolean, block: () -> Unit) :
    Timer {
    private val timeoutId = if (repeat) {
        window.setInterval({ block }, delay.milliseconds.toInt())
    } else {
        window.setTimeout({ block }, delay.milliseconds.toInt())
    }

    actual override fun cancel() {
        if (repeat) window.clearInterval(timeoutId) else window.clearTimeout(timeoutId)
    }
}
