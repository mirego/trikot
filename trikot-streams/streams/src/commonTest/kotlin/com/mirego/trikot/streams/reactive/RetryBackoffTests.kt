package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.backoff.ExponentialBackoffPolicy
import com.mirego.trikot.streams.utils.MockTimer
import com.mirego.trikot.streams.utils.MockTimerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.hours
import kotlin.time.minutes
import kotlin.time.seconds

class RetryBackoffTests {
    @Test
    fun retryBackoff_successAfterFirstRetry() {
        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, duration ->
            assertEquals(Duration.minutes(1), duration)
            MockTimer().also { timers.add(it) }
        }
        var attempt: Int by atomic(0)
        val upStreamPublisher = ColdPublisher {
            val result = when (val value = attempt) {
                0 -> Publishers.error(Throwable())
                else -> value.just()
            }
            attempt = attempt + 1
            result
        }
        val retryPublisher = upStreamPublisher.retryBackoff(
            ExponentialBackoffPolicy(
                initialInterval = Duration.minutes(1),
                maxRetries = 8,
                maxInterval = Duration.hours(1),
                multiplier = 2.0
            ),
            timerFactory
        )
        val receivedResults = mutableListOf<Int>()
        val receivedErrors = mutableListOf<Throwable>()
        retryPublisher.subscribe(
            CancellableManager(),
            onNext = { receivedResults.add(it) },
            onError = { receivedErrors.add(it) },
            onCompleted = {}
        )

        assertEquals(1, timerFactory.singleCall)
        timers[0].executeBlock()
        assertEquals(listOf(1), receivedResults)
        assertEquals(emptyList(), receivedErrors)
    }

    @Test
    fun retryBackoff_mixErrorsAndSuccess() {
        val timers = mutableListOf<MockTimer>()
        var attempt: Int by atomic(0)
        val timerFactory = MockTimerFactory { _, duration ->
            when (timers.size) {
                0 -> assertEquals(Duration.minutes(1), duration)
                1 -> assertEquals(Duration.minutes(2), duration)
            }
            MockTimer().also { timers.add(it) }
        }
        val sourcePublisher1 = Publishers.behaviorSubject(0)
        val sourcePublisher2 = Publishers.behaviorSubject(1)
        val upStreamPublisher = ColdPublisher {
            val result = when (val value = attempt) {
                0 -> sourcePublisher1
                1 -> sourcePublisher2
                else -> value.just()
            }
            attempt = attempt + 1
            result
        }
        val retryPublisher = upStreamPublisher.retryBackoff(
            ExponentialBackoffPolicy(
                initialInterval = Duration.minutes(1),
                maxRetries = 8,
                maxInterval = Duration.hours(1),
                multiplier = 2.0
            ),
            timerFactory
        )
        val receivedResults = mutableListOf<Int>()
        val receivedErrors = mutableListOf<Throwable>()
        retryPublisher.subscribe(
            CancellableManager(),
            onNext = { receivedResults.add(it) },
            onError = { receivedErrors.add(it) },
            onCompleted = {}
        )

        assertEquals(listOf(0), receivedResults)
        assertEquals(emptyList(), receivedErrors)

        sourcePublisher1.error = Throwable()

        assertEquals(1, timerFactory.singleCall)
        timers[0].executeBlock()

        assertEquals(listOf(0, 1), receivedResults)
        assertEquals(emptyList(), receivedErrors)

        sourcePublisher2.error = Throwable()

        assertEquals(2, timerFactory.singleCall)
        timers[1].executeBlock()

        assertEquals(listOf(0, 1, 2), receivedResults)
        assertEquals(emptyList(), receivedErrors)
    }

    @Test
    fun retryBackoff_allErrors_withMaxRetries() {
        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, duration ->
            when (timers.size) {
                0 -> assertEquals(Duration.seconds(1), duration)
                1 -> assertEquals(Duration.minutes(1), duration)
                2 -> assertEquals(Duration.seconds(1), duration)
                3 -> assertEquals(Duration.minutes(2), duration)
                4 -> assertEquals(Duration.seconds(1), duration)
                5 -> assertEquals(Duration.minutes(4), duration)
                6 -> assertEquals(Duration.seconds(1), duration)
                7 -> assertEquals(Duration.minutes(8), duration)
            }
            MockTimer().also { timers.add(it) }
        }
        val upStreamPublisher = ColdPublisher {
            Publishers.error<Int>(Throwable()).delay(Duration.seconds(1), timerFactory)
        }

        val retryPublisher = upStreamPublisher.retryBackoff(
            ExponentialBackoffPolicy(
                initialInterval = Duration.minutes(1),
                maxRetries = 4,
                maxInterval = Duration.hours(1),
                multiplier = 2.0
            ),
            timerFactory
        )

        val receivedResults = mutableListOf<Int>()
        val receivedErrors = mutableListOf<Throwable>()
        retryPublisher.subscribe(
            CancellableManager(),
            onNext = { receivedResults.add(it) },
            onError = { receivedErrors.add(it) },
            onCompleted = {}
        )

        assertEquals(emptyList(), receivedResults)
        assertEquals(emptyList(), receivedErrors)

        (0..8).forEach {
            timers[it].executeBlock()
        }

        assertEquals(emptyList(), receivedResults)
        assertEquals(1, receivedErrors.size)
    }
}
