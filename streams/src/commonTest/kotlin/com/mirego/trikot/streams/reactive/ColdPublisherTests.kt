package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ColdPublisherTests {
    @Test
    fun blockIsExecutedOnSubscription() {
        var executed = false
        val coldPublisher = ColdPublisher<String>({
            executed = true
            Publishers.behaviorSubject()
        })
        coldPublisher.subscribe(CancellableManager()) {}
        assertTrue { executed }
    }

    @Test
    fun cancelledOnUnsubscription() {
        var executed = false
        var cancelled = false
        val coldPublisher = ColdPublisher<String>({
            executed = true
            it.add { cancelled = true }
            Publishers.behaviorSubject()
        })
        val subscription = CancellableManager()
        coldPublisher.subscribe(subscription) {}
        subscription.cancel()
        assertTrue { executed }
        assertTrue { cancelled }
    }

    @Test
    fun valueIsForwarded() {
        var publisher = Publishers.behaviorSubject<String>()
        val coldPublisher = ColdPublisher<String>({
            publisher
        })
        var value: String? = null
        coldPublisher.subscribe(CancellableManager()) { value = it }
        publisher.value = "a"
        assertEquals("a", value)
    }

    @Test
    fun blockIsReexecutedIfResubscribed() {
        var executionCount = 0

        val coldPublisher = ColdPublisher<String>({
            executionCount += 1
            Publishers.behaviorSubject()
        })

        val cancellableManager = CancellableManager()
        coldPublisher.subscribe(cancellableManager) {}
        cancellableManager.cancel()
        coldPublisher.subscribe(CancellableManager()) {}

        assertEquals(2, executionCount)
    }
}
