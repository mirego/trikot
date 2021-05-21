package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.filter
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterProcessorTests {
    @Test
    fun filterTrue() {
        val publisher = Publishers.behaviorSubject("a string")
        var value: String? = null

        publisher.filter { it.isNotBlank() }.subscribe(CancellableManager()) {
            value = it
        }

        assertEquals("a string", value)
    }

    @Test
    fun filterFalse() {
        val publisher = Publishers.behaviorSubject("")
        var value: String? = null
        publisher.filter { it.isNotBlank() }.subscribe(CancellableManager()) {
            value = it
        }

        assertEquals(null, value)
    }
}
