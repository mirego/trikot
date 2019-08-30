package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
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
}
