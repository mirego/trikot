package com.mirego.trikot.streams.reactive.backoff

import com.mirego.trikot.foundation.concurrent.atomic
import kotlin.time.Duration
import kotlin.time.milliseconds
import kotlin.time.minutes

private fun min(a: Duration, b: Duration): Duration {
    return if (a <= b) a else b
}

class ExponentialBackoffPolicy(
    private val initialInterval: Duration = 500.milliseconds,
    private val maxRetries: Int? = null,
    private val maxInterval: Duration = 1.minutes,
    private val multiplier: Double = 1.5
) : BackoffPolicy {
    private var currentInterval: Duration by atomic(initialInterval)
    private var currentIteration: Int by atomic(0)

    override fun reset() {
        currentInterval = initialInterval
        currentIteration = 0
    }

    override fun nextBackoff(): Backoff {
        if (currentIteration == maxRetries) return Backoff.Stop
        val backoff = Backoff.Next(currentInterval)
        currentIteration++
        currentInterval = min(currentInterval * multiplier, maxInterval)
        return backoff
    }
}
