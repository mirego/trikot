package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.merge
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MergeProcessorTests {

    @Test
    fun whenAllPublishersCompleteMergeCompletes() {
        val firstPublisher = Publishers.behaviorSubject<String>()
        val secondPublisher = Publishers.behaviorSubject<String>()
        val receivedValues = mutableListOf<String>()
        var completed = false
        firstPublisher.merge(secondPublisher).subscribe(
            CancellableManager(),
            onNext = { receivedValues += it },
            onError = { throw IllegalStateException() },
            onCompleted = { completed = true }
        )

        firstPublisher.value = "1"
        secondPublisher.value = "2"
        firstPublisher.complete()
        secondPublisher.complete()

        assertEquals(listOf("1", "2"), receivedValues)
        assertTrue(completed)
    }

    @Test
    fun mergeWaitsForAllPublishersToComplete() {
        val firstPublisher = Publishers.just("1")
        val secondPublisher = Publishers.behaviorSubject("2")
        val receivedValues = mutableListOf<String>()
        var completed = false
        firstPublisher.merge(secondPublisher).subscribe(
            CancellableManager(),
            onNext = { receivedValues += it },
            onError = { throw IllegalStateException() },
            onCompleted = { completed = true }
        )

        assertTrue(receivedValues.containsAll(listOf("1", "2")))
        assertFalse(completed)

        secondPublisher.complete()
        assertTrue(completed)
    }

    @Test
    fun mergeDispatchErrorsImmediately() {
        val expectedError = Throwable()
        val firstPublisher = Publishers.behaviorSubject<String>()
        val secondPublisher = Publishers.behaviorSubject<String>()
        val receivedValues = mutableListOf<String>()
        var receivedError: Throwable? = null

        firstPublisher.merge(secondPublisher).subscribe(
            CancellableManager(),
            onNext = { receivedValues += it },
            onError = { receivedError = it },
            onCompleted = { throw IllegalStateException() }
        )

        firstPublisher.value = "1"
        secondPublisher.error = expectedError
        firstPublisher.value = "2"

        assertEquals(listOf("1"), receivedValues)
        assertEquals(expectedError, receivedError)
    }

    @Test
    fun mergeWithALotOfPublishersWaitOnCompletionToComplete() {
        val firstPublisher = Publishers.behaviorSubject("1")
        val allPublishers = (2..100).map { it.toString().just() }
        val receivedValues = mutableListOf<String>()
        var completed = false
        firstPublisher.merge(allPublishers).subscribe(
            CancellableManager(),
            onNext = { receivedValues += it },
            onError = { throw IllegalStateException() },
            onCompleted = { completed = true }
        )

        assertEquals(100, receivedValues.size)
        assertFalse(completed)

        firstPublisher.complete()
        assertTrue(completed)
    }
}
