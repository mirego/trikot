package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JustPublisherTests {

    @Test
    fun justPublisherEmitNextAndCompletion() {
        val publisher = Publishers.just("VALUE")
        var value = ""
        var completion = false
        publisher.subscribe(
            CancellableManager(),
            onNext = { value = it },
            onError = { throw IllegalStateException() },
            onCompleted = { completion = true }
        )
        assertEquals("VALUE", value)
        assertTrue(completion)
    }

    @Test
    fun justPublisherEmitMultipleNextAndCompletion() {
        val publisher = Publishers.justMany("VALUE1", "VALUE2")
        val values = mutableListOf<String>()
        var completion = false
        publisher.subscribe(
            CancellableManager(),
            onNext = { values += it },
            onError = { throw IllegalStateException() },
            onCompleted = { completion = true }
        )
        assertEquals(listOf("VALUE1", "VALUE2"), values)
        assertTrue(completion)
    }
}
