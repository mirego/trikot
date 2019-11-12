package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.reactive.PublisherResultAccumulator
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.asPublisher
import com.mirego.trikot.streams.reactive.assertEquals
import com.mirego.trikot.streams.reactive.withPreviousValue
import kotlin.test.Test
import kotlin.test.assertEquals

class WithPreviousValueProcessorTests {
    @Test
    fun withoutPreviousValue() {
        "a".asPublisher().withPreviousValue().assertEquals(Pair(null, "a"))
    }

    @Test
    fun withPreviousValue() {
        val publisher = Publishers.behaviorSubject("a")
        val accumulator = PublisherResultAccumulator(publisher.withPreviousValue())
        publisher.value = "b"
        assertEquals(listOf(Pair(null, "a"), Pair("a", "b")), accumulator.values)
    }
}
