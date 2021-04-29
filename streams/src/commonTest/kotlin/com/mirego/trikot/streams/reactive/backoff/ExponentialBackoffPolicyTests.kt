package com.mirego.trikot.streams.reactive.backoff

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.hours
import kotlin.time.minutes
import kotlin.time.seconds

class ExponentialBackoffPolicyTests {
    @Test
    fun exponentialBackoff_withMaxRetry() {
        val backoffPolicy = ExponentialBackoffPolicy(
            initialInterval = 1.minutes,
            maxRetries = 8,
            maxInterval = 1.hours,
            multiplier = 2.0
        )

        (1..2).forEach { _ ->
            val backoffs = (1..10).map { backoffPolicy.nextBackoff() }
            assertBackoffDuration(1.minutes, backoffs[0])
            assertBackoffDuration(2.minutes, backoffs[1])
            assertBackoffDuration(4.minutes, backoffs[2])
            assertBackoffDuration(8.minutes, backoffs[3])
            assertBackoffDuration(16.minutes, backoffs[4])
            assertBackoffDuration(32.minutes, backoffs[5])
            assertBackoffDuration(1.hours, backoffs[6])
            assertBackoffDuration(1.hours, backoffs[7])
            assertBackoffStop(backoffs[8])
            assertBackoffStop(backoffs[9])

            backoffPolicy.reset()
        }
    }

    @Test
    fun exponentialBackoff_multiplierIsOneWithoutMaxRetry() {
        val backoffPolicy = ExponentialBackoffPolicy(
            initialInterval = 1.seconds,
            maxInterval = Duration.INFINITE,
            multiplier = 1.0
        )
        val backoffs = (1..1000).map { backoffPolicy.nextBackoff() }
        backoffs.forEach {
            assertBackoffDuration(1.seconds, it)
        }
    }

    private fun assertBackoffDuration(duration: Duration, backoff: Backoff) {
        assertTrue(backoff is Backoff.Next)
        assertEquals(duration, backoff.duration)
    }

    private fun assertBackoffStop(backoff: Backoff) {
        assertTrue(backoff is Backoff.Stop)
    }
}
