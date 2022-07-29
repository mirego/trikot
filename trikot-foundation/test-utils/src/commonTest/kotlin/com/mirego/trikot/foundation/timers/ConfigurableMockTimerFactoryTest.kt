package com.mirego.trikot.foundation.timers

import ConfigurableMockTimerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds

class ConfigurableMockTimerFactoryTest {
    @Test
    fun singleWithSetTime() {
        val mockTimer = ConfigurableMockTimerFactory()
        var fired = false
        mockTimer.single(500.milliseconds) {
            fired = true
        }
        mockTimer.setTime(500.milliseconds)
        assertTrue(fired)
    }

    @Test
    fun singleAddTime() {
        val mockTimer = ConfigurableMockTimerFactory()
        var fired = false
        mockTimer.single(500.milliseconds) {
            fired = true
        }
        mockTimer.addToTime(500.milliseconds)
        assertTrue(fired)
    }

    @Test
    fun repeatableWithSetTime() {
        val mockTimer = ConfigurableMockTimerFactory()
        var counter = 0
        mockTimer.repeatable(500.milliseconds) {
            counter++
        }
        mockTimer.setTime(500.milliseconds)
        assertEquals(1, counter)

        mockTimer.setTime(1000.milliseconds)
        assertEquals(2, counter)
    }

    @Test
    fun repeatableWithAddToTime() {
        val mockTimer = ConfigurableMockTimerFactory()
        var counter = 0
        mockTimer.repeatable(500.milliseconds) {
            counter++
        }
        mockTimer.addToTime(500.milliseconds)
        assertEquals(1, counter)

        mockTimer.addToTime(500.milliseconds)
        assertEquals(2, counter)
    }
}
