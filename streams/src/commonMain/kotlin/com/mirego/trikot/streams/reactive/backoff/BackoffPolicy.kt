package com.mirego.trikot.streams.reactive.backoff

import kotlin.time.Duration

sealed class Backoff {
    object Stop : Backoff()
    data class Next(val duration: Duration) : Backoff()
}

interface BackoffPolicy {
    fun reset()
    fun nextBackoff(): Backoff
}
