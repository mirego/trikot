package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.delay
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.utils.MockTimer
import com.mirego.trikot.streams.utils.MockTimerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalTime
class DelayProcessorTests {

    @Test
    fun testEveryUpstreamValueIsDelayedByTheSameAmountOfTime() {
        val publisher = Publishers.behaviorSubject<String>()

        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, _ -> MockTimer().also { timers.add(it) } }

        val receivedResults = mutableListOf<String>()
        publisher
            .delay(Duration.seconds(5), timerFactory)
            .subscribe(CancellableManager()) {
                receivedResults.add(it)
            }

        publisher.value = "a"
        publisher.value = "b"
        publisher.value = "c"

        timers.forEach(MockTimer::executeBlock)

        assertEquals(listOf("a", "b", "c"), receivedResults)
        assertEquals(3, timerFactory.singleCall)
    }

    @Test
    fun testUpstreamErrorIsDelayed() {
        val publisher = Publishers.behaviorSubject("a")
        val error = Throwable()

        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, _ -> MockTimer().also { timers.add(it) } }

        val receivedValues = mutableListOf<String>()
        val receivedErrors = mutableListOf<Throwable>()
        publisher
            .delay(Duration.seconds(5), timerFactory)
            .subscribe(
                CancellableManager(),
                onNext = { receivedValues.add(it) },
                onError = { receivedErrors.add(it) }
            )

        publisher.value = "b"
        publisher.error = error

        assertEquals(3, timerFactory.singleCall)

        timers.forEach(MockTimer::executeBlock)

        assertEquals(listOf("a", "b"), receivedValues)
        assertEquals(listOf(error), receivedErrors)
    }

    @Test
    fun testCompletionIsDelayed() {
        val publisher = Publishers.just("a")

        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, _ -> MockTimer().also { timers.add(it) } }

        val receivedResults = mutableListOf<String>()
        var completed = false
        publisher
            .delay(Duration.seconds(5), timerFactory)
            .subscribe(
                CancellableManager(),
                onNext = { receivedResults.add(it) },
                onError = { },
                onCompleted = { completed = true }
            )

        assertEquals(2, timerFactory.singleCall)

        timers[0].executeBlock()
        assertEquals(listOf("a"), receivedResults)
        assertFalse(completed)

        timers[1].executeBlock()
        assertEquals(listOf("a"), receivedResults)
        assertTrue(completed)
    }

    @Test
    fun testTimersAreCancelledWhenSubscriptionIsCancelled() {
        val values = listOf("a", "b", "c")
        val publisher = Publishers.fromIterable(values)

        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, _ -> MockTimer().also { timers.add(it) } }

        val cancellableManager = CancellableManager()
        val receivedResults = mutableListOf<String>()
        publisher
            .delay(Duration.seconds(5), timerFactory)
            .subscribe(cancellableManager) {
                receivedResults.add(it)
            }

        timers.forEachIndexed { index, mockTimer ->
            mockTimer.executeBlock()

            if (index == 1) { // Cancel subscription after second element "b" is received
                cancellableManager.cancel()
            }
        }

        assertEquals(listOf("a", "b"), receivedResults)
        timers.forEach { assertTrue(it.isCancelled) }
    }
}
