package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.MockPublisher
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.onErrorResumeNext
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OnErrorResumeNextProcessorTests {
    @Test
    fun onErrorResumeNext_withNextAndCompletion() {
        val publisher = Publishers.behaviorSubject<String>()
        val expectedError = Exception()
        publisher.error = expectedError
        val expectedValue = "EXPECTED_VALUE"
        var receivedError: Throwable? = null
        var receivedValue: String? = null
        var isCompleted = false

        publisher.onErrorResumeNext {
            receivedError = it
            expectedValue.just()
        }.subscribe(
            CancellableManager(),
            onNext = { receivedValue = it },
            onError = { throw IllegalStateException("Should not return an error") },
            onCompleted = { isCompleted = true }
        )

        assertEquals(expectedError, receivedError)
        assertEquals(expectedValue, receivedValue)
        assertTrue(isCompleted, "Should be completed")
    }

    @Test
    fun onErrorResumeNext_withNextAndNoCompletion() {
        val publisher = Publishers.behaviorSubject<String>()
        val expectedError = Exception()
        publisher.error = expectedError
        val expectedValue = "EXPECTED_VALUE"
        var receivedError: Throwable? = null
        var receivedValue: String? = null
        var isCompleted = false

        publisher.onErrorResumeNext {
            receivedError = it
            Publishers.behaviorSubject(expectedValue)
        }.subscribe(
            CancellableManager(),
            onNext = { receivedValue = it },
            onError = { throw IllegalStateException("Should not return an error") },
            onCompleted = { isCompleted = true }
        )

        assertEquals(expectedError, receivedError)
        assertEquals(expectedValue, receivedValue)
        assertFalse(isCompleted, "Should be not be completed")
    }

    @Test
    fun onErrorResumeNext_withError() {
        val publisher = Publishers.behaviorSubject<String>()
        val expectedError = Exception()
        publisher.error = expectedError
        val resumeError = Exception()
        var receivedError: Throwable? = null
        var receivedResumeError: Throwable? = null
        var isCompleted = false

        publisher.onErrorResumeNext {
            receivedError = it
            Publishers.error(resumeError)
        }.subscribe(
            CancellableManager(),
            onNext = { throw IllegalStateException("Should not return a value") },
            onError = { receivedResumeError = it },
            onCompleted = { isCompleted = true }
        )

        assertEquals(expectedError, receivedError)
        assertEquals(resumeError, receivedResumeError)
        assertFalse(isCompleted, "Should not be completed")
    }

    @Test
    fun onErrorResumeNext_withEmpty() {
        val publisher = Publishers.behaviorSubject<String>()
        val expectedError = Exception()
        publisher.error = expectedError
        var isCompleted = false

        publisher.onErrorResumeNext {
            Publishers.empty()
        }.subscribe(
            CancellableManager(),
            onNext = { throw IllegalStateException("Should not return a value") },
            onError = { throw IllegalStateException("Should not return an error") },
            onCompleted = { isCompleted = true }
        )

        assertTrue(isCompleted, "Should be completed")
    }

    @Test
    fun parentIsUnsubscribedOnError() {
        val parentPublisher = MockPublisher().also { it.error = Throwable() }
        parentPublisher.onErrorResumeNext { "a".just() }.subscribe(CancellableManager()) {
        }

        assertFalse(parentPublisher.getHasSubscriptions)
    }
}
