package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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

    @Test
    fun testMappingStreamsProcessorException() {
        val publisher = Publishers.behaviorSubject("a")
        val expectedException = StreamsProcessorException()
        var receivedException: StreamsProcessorException? = null

        publisher.map { throw expectedException }.subscribe(CancellableManager(), onNext = {
        }, onError = { receivedException = it as StreamsProcessorException })

        assertEquals(expectedException, receivedException)
    }

    @Test
    fun testMappingAnyException() {
        val publisher = Publishers.behaviorSubject("a")
        @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
        var receivedException: StreamsProcessorException? = null

        assertFailsWith(IllegalStateException::class) {
            publisher.map { throw IllegalStateException() }.subscribe(CancellableManager(), onNext = {
            }, onError = { receivedException = it as StreamsProcessorException })
        }
    }
}
