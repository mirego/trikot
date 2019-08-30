package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals

class MapProcessorTests {
    @Test
    fun testMapping() {
        val publisher = Publishers.behaviorSubject("a")
        val expectedResult = 1234
        var receivedResult: Int? = null

        publisher.map { expectedResult }.subscribe(CancellableManager()) {
            receivedResult = it
        }

        assertEquals(expectedResult, receivedResult)
    }
}
