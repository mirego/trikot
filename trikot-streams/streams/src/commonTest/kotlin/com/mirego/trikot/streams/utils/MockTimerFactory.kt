package com.mirego.trikot.streams.utils

import com.mirego.trikot.foundation.timers.Timer
import com.mirego.trikot.foundation.timers.TimerFactory
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MockTimerFactory(private val creationBlock: (Boolean, Duration) -> MockTimer) : TimerFactory {
    var repeatableCall = 0
    var singleCall = 0

    override fun repeatable(delay: Duration, block: () -> Unit): Timer {
        repeatableCall++
        val timer = creationBlock(true, delay)
        timer.block = block
        return timer
    }

    override fun single(delay: Duration, block: () -> Unit): Timer {
        singleCall++
        val timer = creationBlock(false, delay)
        timer.block = block
        return timer
    }
}

class MockTimer : Timer {
    var isCancelled = false
    var block: () -> Unit = noBlock

    fun executeBlock() {
        block.invoke()
    }

    override fun cancel() {
        isCancelled = true
    }
}

val noBlock = {}
