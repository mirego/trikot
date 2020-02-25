package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.MockPublisher
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.shared
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedProcessorTests {
    @Test
    fun resultIsShared() {
        val publisher = Publishers.behaviorSubject("a")
        var executionCount = 0

        publisher.map {
            executionCount++
            it
        }.shared().also {
            it.subscribe(CancellableManager()) {}
            it.subscribe(CancellableManager()) {}
        }

        assertEquals(1, executionCount)
    }

    @Test
    fun resultIsForgottenWhenNoSubscriptionsAreActive() {
        val publisher = Publishers.behaviorSubject("a")
        var executionCount = 0

        val cancellableManager = CancellableManager()

        publisher.map {
            executionCount++
            it
        }.shared().also {
            it.subscribe(cancellableManager) {}
            cancellableManager.cancel()
            it.subscribe(CancellableManager()) {}
        }

        assertEquals(2, executionCount)
    }

    @Test
    fun resultIsKeptWhenNoSubscriptionsAreActiveAndParentPublisherIsCompleted() {
        val publisher = Publishers.behaviorSubject("a")
        var executionCount = 0
        var firstSubscriptionValueReceived: String? = null
        var secondSubscriptionValueReceived: String? = null
        var isCompleted = false

        val cancellableManager = CancellableManager()

        val sharedProcessor = publisher.map {
            executionCount++
            it
        }.shared()

        publisher.complete()
        sharedProcessor.subscribe(cancellableManager,
            onNext = { firstSubscriptionValueReceived = it },
            onError = {},
            onCompleted = { isCompleted = true })
        cancellableManager.cancel()
        sharedProcessor.subscribe(CancellableManager()) { secondSubscriptionValueReceived = it }

        assertEquals(true, isCompleted)
        assertEquals("a", firstSubscriptionValueReceived)
        assertEquals(1, executionCount)
        assertEquals("a", secondSubscriptionValueReceived)
    }

    @Test
    fun processorUnsubscribeFromParentOnError() {
        val masterPublisher = MockPublisher("a")
        val erroredPublisher = masterPublisher.map { throw StreamsProcessorException() }
        val sharedPublisher = erroredPublisher.shared()
        masterPublisher.subscribe(CancellableManager()) {}

        var errorCount = 0
        sharedPublisher.subscribe(CancellableManager(), {},
            {
                errorCount++
                masterPublisher.value = "b"
            }
        )

        assertEquals(1, errorCount)
    }
}
