package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RefreshablePublisherTests {
    @Test
    fun blockIsExecutedOnSubscription() {
        var executed = false
        var refreshValue = false
        val refreshable = RefreshablePublisher<String>({ _, refreshing ->
            executed = true
            refreshValue = refreshing
            Publishers.behaviorSubject()
        })
        refreshable.subscribe(CancellableManager()) {}
        assertTrue { executed }
        assertFalse { refreshValue }
    }

    @Test
    fun cancelledOnUnsubscription() {
        var executed = false
        var cancelled = false
        var refreshValue = false

        val refreshable = RefreshablePublisher<String>({ cancellable, refreshing ->
            executed = true
            refreshValue = refreshing
            cancellable.add { cancelled = true }
            Publishers.behaviorSubject()
        })
        val subscription = CancellableManager()
        refreshable.subscribe(subscription) {}
        subscription.cancel()
        assertTrue { executed }
        assertTrue { cancelled }
        assertFalse { refreshValue }
    }

    @Test
    fun valueIsForwarded() {
        var publisher = Publishers.behaviorSubject<String>()
        val refreshable = RefreshablePublisher({ _, _ ->
            publisher
        })
        var value: String? = null

        refreshable.subscribe(CancellableManager()) { value = it }

        publisher.value = "a"
        assertEquals("a", value)
    }

    @Test
    fun blockIsReexecutedIfResubscribed() {
        var refreshValue = false
        var executionCount = 0
        val rereshable = RefreshablePublisher<String>({ _, refreshing ->
            executionCount += 1
            refreshValue = refreshing
            Publishers.behaviorSubject()
        })
        val cancellableManager = CancellableManager()

        rereshable.subscribe(cancellableManager) {}
        cancellableManager.cancel()
        rereshable.subscribe(CancellableManager()) {}

        assertEquals(2, executionCount)
        assertFalse { refreshValue }
    }

    @Test
    fun blockIsReExecutedWhenRefreshed() {
        var executed = false
        var refreshValue = false
        val refreshable = RefreshablePublisher<String>({ _, refreshing ->
            executed = true
            refreshValue = refreshing
            Publishers.behaviorSubject()
        })

        refreshable.subscribe(CancellableManager()) {}
        refreshable.refresh()

        assertTrue { executed }
        assertTrue { refreshValue }
    }

    @Test
    fun blockIsExecutedWithRefreshValueWhenRefreshedAndNoSubscription() {
        var executed = false
        var refreshValue = false
        val refreshable = RefreshablePublisher<String>({ _, refreshing ->
            executed = true
            refreshValue = refreshing
            Publishers.behaviorSubject()
        })

        refreshable.refresh()
        refreshable.subscribe(CancellableManager()) {}

        assertTrue { executed }
        assertTrue { refreshValue }
    }
}
