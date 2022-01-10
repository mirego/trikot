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
            initialInterval = Duration.minutes(1),
            maxRetries = 8,
            maxInterval = Duration.hours(1),
            multiplier = 2.0
        )

        (1..2).forEach { _ ->
            val backoffs = (1..10).map { backoffPolicy.nextBackoff() }
            assertBackoffDuration(Duration.minutes(1), backoffs[0])
            assertBackoffDuration(Duration.minutes(2), backoffs[1])
            assertBackoffDuration(Duration.minutes(4), backoffs[2])
            assertBackoffDuration(Duration.minutes(8), backoffs[3])
            assertBackoffDuration(Duration.minutes(16), backoffs[4])
            assertBackoffDuration(Duration.minutes(32), backoffs[5])
            assertBackoffDuration(Duration.hours(1), backoffs[6])
            assertBackoffDuration(Duration.hours(1), backoffs[7])
            assertBackoffStop(backoffs[8])
            assertBackoffStop(backoffs[9])

            backoffPolicy.reset()
        }
    }

    @Test
    fun exponentialBackoff_multiplierIsOneWithoutMaxRetry() {
        val backoffPolicy = ExponentialBackoffPolicy(
            initialInterval = Duration.seconds(1),
            maxInterval = Duration.INFINITE,
            multiplier = 1.0
        )
        val backoffs = (1..1000).map { backoffPolicy.nextBackoff() }
        backoffs.forEach {
            assertBackoffDuration(Duration.seconds(1), it)
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
