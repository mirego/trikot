package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.reactive.withPreviousValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class WithPreviousValueProcessorTests {
    @Test
    fun withoutPreviousValue() {
        val publisher = Publishers.behaviorSubject("a")
        var previousValue: String? = null
        var actualValue: String? = null

        publisher.withPreviousValue().subscribe(CancellableManager()) {
            previousValue = it.first
            actualValue = it.second
        }

        assertNull(previousValue)
        assertEquals("a", actualValue)
    }

    @Test
    fun withPreviousValue() {
        val publisher = Publishers.behaviorSubject("a")
        var previousValue: String? = null
        var actualValue: String? = null

        publisher.withPreviousValue().subscribe(CancellableManager()) {
            previousValue = it.first
            actualValue = it.second
        }
        publisher.value = "b"

        assertEquals("a", previousValue)
        assertEquals("b", actualValue)
    }
}
