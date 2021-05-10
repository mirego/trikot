package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.utils.MockTimer
import com.mirego.trikot.streams.utils.MockTimerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.seconds

class TimerPublisherTests {

    @Test
    fun testTimerPublisher_doesNothingIfNotSubscribed() {
        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, _ -> MockTimer().also { timers.add(it) } }

        Publishers.timer(Duration.seconds(10), timerFactory)
        assertEquals(0, timerFactory.singleCall)
    }

    @Test
    fun testTimerPublisher_emitsAfterSubscriptionDelay() {
        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, duration ->
            assertEquals(Duration.seconds(10), duration)
            MockTimer().also { timers.add(it) }
        }
        val timerPublisher = Publishers.timer(Duration.seconds(10), timerFactory)

        val receivedResults = mutableListOf<Long>()
        var completed = false
        timerPublisher
            .subscribe(
                CancellableManager(),
                onNext = { receivedResults.add(it) },
                onError = {},
                onCompleted = { completed = true }
            )

        assertEquals(1, timerFactory.singleCall)
        timers[0].executeBlock()

        assertEquals(listOf(0L), receivedResults)
        assertTrue(completed)
    }

    @Test
    fun testTimerPublisher_doesNotEmitIfCancelledBeforeDelay() {
        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, duration ->
            assertEquals(Duration.seconds(10), duration)
            MockTimer().also { timers.add(it) }
        }
        val timerPublisher = Publishers.timer(Duration.seconds(10), timerFactory)

        val receivedResults = mutableListOf<Long>()
        var completed = false
        val cancellableManager = CancellableManager()
        timerPublisher
            .subscribe(
                cancellableManager,
                onNext = { receivedResults.add(it) },
                onError = {},
                onCompleted = { completed = true }
            )

        assertEquals(1, timerFactory.singleCall)

        cancellableManager.cancel()

        timers[0].executeBlock()

        assertEquals(emptyList(), receivedResults)
        assertFalse(completed)
    }
}
