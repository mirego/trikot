package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.onErrorReturn
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class OnErrorReturnProcessorTests {
    @Test
    fun errorIsMappedAsNext() {
        val publisher = Publishers.behaviorSubject<String>()
        val expectedError = Exception()
        publisher.error = expectedError
        val expectedValue = "EXPECTED_VALUE"
        var receivedError: Throwable? = null
        var receivedValue: String? = null
        var isCompleted = false

        publisher.onErrorReturn {
            receivedError = it
            expectedValue
        }.subscribe(CancellableManager(),
            onNext = { receivedValue = it },
            onError = { throw IllegalStateException("Should not return an error") }

        ) { isCompleted = true }

        assertEquals(expectedError, receivedError)
        assertEquals(expectedValue, receivedValue)
        assertFalse("Should not be completed") { isCompleted }
    }
}
