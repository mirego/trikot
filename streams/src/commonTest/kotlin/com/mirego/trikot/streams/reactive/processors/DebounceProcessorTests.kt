package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.timers.Timer
import com.mirego.trikot.foundation.timers.TimerFactory
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.debounce
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
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
            .debounce(600.milliseconds, MockTimerFactory(timer))
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
            .debounce(600.milliseconds, MockTimerFactory(timer))
            .subscribe(CancellableManager().also { it.cancel() }) {
                receivedResult = it
            }
        timer.executeBlock()
        publisher.value = expectedResult
        assertEquals(null, receivedResult)
        assertEquals(NoBlock, timer.block)
    }
}

@ExperimentalTime
class MockTimerFactory(private val mockTimer: MockTimer) : TimerFactory {
    override fun repeatable(delay: Duration, block: () -> Unit): Timer {
        mockTimer.block = block
        return mockTimer
    }

    override fun single(delay: Duration, block: () -> Unit): Timer {
        mockTimer.block = block
        return mockTimer
    }
}

class MockTimer : Timer {

    var block: () -> Unit = NoBlock

    fun executeBlock() {
        block.invoke()
    }

    override fun cancel() {
    }
}

object NoBlock : () -> Unit {
    override fun invoke() {}
}
