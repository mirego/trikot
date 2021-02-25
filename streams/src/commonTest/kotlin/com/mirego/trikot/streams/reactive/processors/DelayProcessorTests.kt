package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.delay
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.utils.MockTimer
import com.mirego.trikot.streams.utils.MockTimerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalTime
class DelayProcessorTests {

    @Test
    fun testEveryUpstreamValueIsDelayedByTheSameAmountOfTime() {
        val values = listOf("a", "b", "c")
        val publisher = Publishers.fromIterable(values)

        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, _ -> MockTimer().also { timers.add(it) } }

        val receivedResults = mutableListOf<String>()
        publisher
            .delay(5.seconds, timerFactory)
            .subscribe(CancellableManager()) {
                receivedResults.add(it)
            }

        timers.forEachIndexed { index, mockTimer ->
            mockTimer.executeBlock()
            assertEquals(values[index], receivedResults[index])
        }

        assertEquals(values.size, timerFactory.singleCall)
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
            .delay(5.seconds, timerFactory)
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

    @Test
    fun testTimersAreCancelledWhenUpstreamCompletesBeforeTimersCompletion() {
        val publisher = Publishers.behaviorSubject<String>()

        val timers = mutableListOf<MockTimer>()
        val timerFactory = MockTimerFactory { _, _ -> MockTimer().also { timers.add(it) } }

        val receivedResults = mutableListOf<String>()
        var error: Throwable? = null
        var completed = false
        publisher
            .delay(5.seconds, timerFactory)
            .subscribe(
                CancellableManager(),
                onNext = {
                    receivedResults.add(it)
                },
                onError = {
                    error = it
                },
                onCompleted = {
                    completed = true
                }
            )

        publisher.value = "a"
        timers[0].executeBlock()
        assertEquals(listOf("a"), receivedResults)

        publisher.value = "b"
        timers[1].executeBlock()
        assertEquals(listOf("a", "b"), receivedResults)

        publisher.value = "c"
        // delayed timer does not fire yet!
        publisher.complete()

        assertEquals(listOf("a", "b"), receivedResults)
        assertNull(error)
        assertTrue(completed)
        assertTrue(timers[2].isCancelled)
    }
}
