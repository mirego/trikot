package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.reject
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals

class RejectProcessorTests {
    @Test
    fun rejectTrue() {
        val publisher = Publishers.behaviorSubject("")
        var value: String? = null

        publisher.reject { it.isBlank() }.subscribe(CancellableManager()) {
            value = it
        }

        assertEquals(null, value)
    }

    @Test
    fun rejectFalse() {
        val publisher = Publishers.behaviorSubject("a string")
        var value: String? = null

        publisher.reject { it.isBlank() }.subscribe(CancellableManager()) {
            value = it
        }

        assertEquals("a string", value)
    }
}
