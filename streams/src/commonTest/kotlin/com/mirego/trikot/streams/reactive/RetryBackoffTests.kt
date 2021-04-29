package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.backoff.ExponentialBackoffPolicy
import com.mirego.trikot.streams.utils.MockTimer
import com.mirego.trikot.streams.utils.MockTimerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.hours
import kotlin.time.minutes
import kotlin.time.seconds

class RetryBackoffTests {
    @Test
    fun retryBackoff_successAfterFirstRetry() {
        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, duration ->
            assertEquals(1.minutes, duration)
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
                initialInterval = 1.minutes,
                maxRetries = 8,
                maxInterval = 1.hours,
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
                0 -> assertEquals(1.minutes, duration)
                1 -> assertEquals(2.minutes, duration)
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
                initialInterval = 1.minutes,
                maxRetries = 8,
                maxInterval = 1.hours,
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
                0 -> assertEquals(1.seconds, duration)
                1 -> assertEquals(1.minutes, duration)
                2 -> assertEquals(1.seconds, duration)
                3 -> assertEquals(2.minutes, duration)
                4 -> assertEquals(1.seconds, duration)
                5 -> assertEquals(4.minutes, duration)
                6 -> assertEquals(1.seconds, duration)
                7 -> assertEquals(8.minutes, duration)
            }
            MockTimer().also { timers.add(it) }
        }
        val upStreamPublisher = ColdPublisher {
            Publishers.error<Int>(Throwable()).delay(1.seconds, timerFactory)
        }

        val retryPublisher = upStreamPublisher.retryBackoff(
            ExponentialBackoffPolicy(
                initialInterval = 1.minutes,
                maxRetries = 4,
                maxInterval = 1.hours,
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
