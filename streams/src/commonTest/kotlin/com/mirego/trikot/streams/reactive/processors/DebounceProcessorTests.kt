package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.debounce
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.utils.MockTimer
import com.mirego.trikot.streams.utils.MockTimerFactory
import com.mirego.trikot.streams.utils.NoBlock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
class DebounceProcessorTests {

    @Test
    fun testDebounceCalled() {
        val publisher = Publishers.behaviorSubject("a")
        val timer = MockTimer()
        val expectedResult = "b"
        var receivedResult: String? = null
        publisher
            .debounce(600.milliseconds, MockTimerFactory { _, _ -> timer })
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
            .debounce(600.milliseconds, MockTimerFactory { _, _ -> timer })
            .subscribe(CancellableManager().also { it.cancel() }) {
                receivedResult = it
            }
        timer.executeBlock()
        publisher.value = expectedResult
        assertEquals(null, receivedResult)
        assertEquals(NoBlock, timer.block)
    }
}
