package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.debounce
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.utils.MockTimer
import com.mirego.trikot.streams.utils.MockTimerFactory
import com.mirego.trikot.streams.utils.noBlock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds
import kotlin.time.minutes

@ExperimentalTime
class DebounceProcessorTests {

    @Test
    fun testDebounceCalled() {
        val publisher = Publishers.behaviorSubject("a")
        val timer = MockTimer()
        val expectedResult = "b"
        var receivedResult: String? = null
        publisher
            .debounce(Duration.milliseconds(600), MockTimerFactory { _, _ -> timer })
            .subscribe(CancellableManager()) {
                receivedResult = it
            }
        publisher.value = expectedResult
        timer.executeBlock()

        assertEquals(expectedResult, receivedResult)
    }

    @Test
    fun testDebounceCanceled() {
        val publisher = Publishers.behaviorSubject("a")
        val timer = MockTimer()
        val expectedResult = "b"
        var receivedResult: String? = null
        publisher
            .debounce(Duration.milliseconds(600), MockTimerFactory { _, _ -> timer })
            .subscribe(CancellableManager().also { it.cancel() }) {
                receivedResult = it
            }
        timer.executeBlock()
        publisher.value = expectedResult
        assertEquals(null, receivedResult)
        assertEquals(noBlock, timer.block)
    }

    @Test
    fun testDebounceCompleted() {
        val expectedResult = "b"
        val publisher = Publishers.behaviorSubject(expectedResult)
        val timer = MockTimer()
        var receivedResult: String? = null
        publisher
            .debounce(Duration.minutes(600), MockTimerFactory { _, _ -> timer })
            .subscribe(CancellableManager()) {
                receivedResult = it
            }
        publisher.value = expectedResult
        publisher.complete()
        assertEquals(expectedResult, receivedResult)
    }
}
