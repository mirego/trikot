package com.mirego.trikot.foundation.timers

import kotlinx.browser.window
import kotlin.time.Duration

actual class PlatformTimer actual constructor(delay: Duration, private val repeat: Boolean, block: () -> Unit) :
    Timer {
    private val timeoutId = if (repeat) {
        window.setInterval({ block() }, delay.inWholeMilliseconds.toInt())
    } else {
        window.setTimeout({ block() }, delay.inWholeMilliseconds.toInt())
    }

    actual override fun cancel() {
        if (repeat) window.clearInterval(timeoutId) else window.clearTimeout(timeoutId)
    }
}
