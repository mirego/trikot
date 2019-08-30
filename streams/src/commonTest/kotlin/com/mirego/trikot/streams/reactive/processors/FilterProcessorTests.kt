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
        val publisher = Publishers.behaviorSubject("a")
        var value: String? = null

        publisher.filter { true }.subscribe(CancellableManager()) {
            value = it
        }

        assertEquals("a", value)
    }

    @Test
    fun filterFalse() {
        val publisher = Publishers.behaviorSubject("a")
        var value: String? = null
        publisher.filter { false }.subscribe(CancellableManager()) {
            value = it
        }

        assertEquals(null, value)
    }
}
