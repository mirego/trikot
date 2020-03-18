package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.MockPublisher
import com.mirego.trikot.streams.reactive.StreamsTimeoutException
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.utils.MockTimer
import com.mirego.trikot.streams.utils.MockTimerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.seconds

class TimeoutProcessorTests {
    val firstMockTimer = MockTimer()
    val secondMockTimer = MockTimer()
    var timeGetCount = 0
    val mockTimerFactory = MockTimerFactory { _, _ ->
        timeGetCount += 1
        if (timeGetCount == 1) {
            firstMockTimer
        } else {
            secondMockTimer
        }
    }
    val publisher = MockPublisher()

    val timeoutProcessor = TimeoutProcessor(2.seconds, mockTimerFactory, "", publisher)

    @Test
    fun givenSubscribedProcessorWhenNoValueIsEmittedAndTimerExpireThenErrorIsThrown() {
        var receivedResult: String? = null
        var receivedError: Throwable? = null

        timeoutProcessor.subscribe(CancellableManager(),
            onNext = {
                receivedResult = it
            },
            onError = {
                receivedError = it
            }
        )

        firstMockTimer.executeBlock()

        assertFalse { publisher.getHasSubscriptions }
        assertNull(receivedResult)
        assertTrue { receivedError is StreamsTimeoutException }
        assertEquals(1, mockTimerFactory.singleCall)
        assertTrue { firstMockTimer.isCancelled }
    }

    @Test
    fun givenSubscribedProcessorWhenValueIsEmittedThenTimerIsResetted() {
        val expectedValue = "ExpectedValue"
        var receivedResult: String? = null
        var receivedError: Throwable? = null

        timeoutProcessor.subscribe(CancellableManager(),
            onNext = {
                receivedResult = it
            },
            onError = {
                receivedError = it
            }
        )

        publisher.value = expectedValue

        assertTrue { publisher.getHasSubscriptions }
        assertNull(receivedError)
        assertEquals(expectedValue, receivedResult)
        assertEquals(2, mockTimerFactory.singleCall)
        assertTrue { firstMockTimer.isCancelled }
        assertFalse { secondMockTimer.isCancelled }
    }

    @Test
    fun givenSubscribedProcessorWhenUnsubscribedThenTimerIsCancelled() {
        val expectedValue = "ExpectedValue"
        var receivedResult: String? = null

        val cancellableManager = CancellableManager()
        timeoutProcessor.subscribe(cancellableManager) { receivedResult = it }

        publisher.value = expectedValue
        cancellableManager.cancel()

        assertFalse { publisher.getHasSubscriptions }
        assertEquals(expectedValue, receivedResult)
        assertEquals(2, mockTimerFactory.singleCall)
        assertTrue { firstMockTimer.isCancelled }
        assertTrue { secondMockTimer.isCancelled }
    }

    @Test
    fun givenSubscribedProcessorWhenCompletedThenTimerIsCancelled() {
        timeoutProcessor.subscribe(CancellableManager()) { }

        publisher.complete()

        assertFalse { publisher.getHasSubscriptions }
        assertEquals(1, mockTimerFactory.singleCall)
        assertTrue { firstMockTimer.isCancelled }
    }
}
