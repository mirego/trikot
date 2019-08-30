package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.mapErrorAsNext
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals

class MapErrorAsNextProcessorTests {
    @Test
    fun errorIsMappedAsNext() {
        val publisher = Publishers.behaviorSubject<String>()
        val expectedError = Exception()
        publisher.error = expectedError
        var receivedError: Throwable? = null

        publisher.mapErrorAsNext {
            receivedError = it
            "a"
        }.subscribe(CancellableManager()) {}

        assertEquals(expectedError, receivedError)
    }
}
