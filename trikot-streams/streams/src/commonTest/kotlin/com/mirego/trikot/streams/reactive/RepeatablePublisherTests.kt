package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.utils.MockTimer
import com.mirego.trikot.streams.utils.MockTimerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

class RepeatablePublisherTests {
    @Test
    fun blockIsExecutedOnSubscription() {
        var executed = false

        val repeatable = Publishers.repeat(1.minutes) {
            executed = true
            Publishers.behaviorSubject<String>()
        }
        repeatable.subscribe(CancellableManager()) {}
        kotlin.test.assertTrue { executed }
    }

    @Test
    fun blockIsReexecutedIfResubscribed() {
        var executionCount by atomic(0)
        val repeatable = Publishers.repeat(1.minutes) {
            executionCount++
            Publishers.behaviorSubject<String>()
        }
        val cancellableManager = CancellableManager()

        repeatable.subscribe(cancellableManager) {}
        cancellableManager.cancel()
        repeatable.subscribe(CancellableManager()) {}

        assertEquals(2, executionCount)
    }

    @Test
    fun blockIsReExecutedWhenRepeated() {
        var executions by atomic(0)
        var timer: MockTimer? = null
        val timerFactory = MockTimerFactory { _, duration ->
            assertEquals(1.minutes, duration)
            MockTimer().also { timer = it }
        }
        val repeatable = Publishers.repeat(1.minutes, timerFactory) {
            executions++
            Publishers.behaviorSubject<String>()
        }

        repeatable.subscribe(CancellableManager()) {}
        timer?.executeBlock()

        assertEquals(2, executions)
    }
}
