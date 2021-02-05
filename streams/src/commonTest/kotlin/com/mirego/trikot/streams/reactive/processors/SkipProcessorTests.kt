package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.skip
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SkipProcessorTests {
    @Test
    fun skipNegativeElements() {
        val values = mutableListOf<String>()
        var completed = false

        Publishers.justMany("a", "b", "c")
            .skip(-99)
            .subscribe(
                CancellableManager(),
                onNext = { values += it },
                onError = {},
                onCompleted = { completed = true }
            )

        assertEquals(listOf("a", "b", "c"), values)
        assertTrue(completed)
    }

    @Test
    fun skipZeroElements() {
        val values = mutableListOf<String>()
        var completed = false

        Publishers.justMany("a", "b", "c")
            .skip(0)
            .subscribe(
                CancellableManager(),
                onNext = { values += it },
                onError = {},
                onCompleted = { completed = true }
            )

        assertEquals(listOf("a", "b", "c"), values)
        assertTrue(completed)
    }

    @Test
    fun skipOneElements() {
        val values = mutableListOf<String>()
        var completed = false

        Publishers.justMany("a", "b", "c")
            .skip(1)
            .subscribe(
                CancellableManager(),
                onNext = { values += it },
                onError = {},
                onCompleted = { completed = true }
            )

        assertEquals(listOf("b", "c"), values)
        assertTrue(completed)
    }

    @Test
    fun skipTwoElements() {
        val values = mutableListOf<String>()
        var completed = false

        Publishers.justMany("a", "b", "c")
            .skip(2)
            .subscribe(
                CancellableManager(),
                onNext = { values += it },
                onError = {},
                onCompleted = { completed = true }
            )

        assertEquals(listOf("c"), values)
        assertTrue(completed)
    }

    @Test
    fun skipThreeElements() {
        val values = mutableListOf<String>()
        var completed = false

        Publishers.justMany("a", "b", "c")
            .skip(3)
            .subscribe(
                CancellableManager(),
                onNext = { values += it },
                onError = {},
                onCompleted = { completed = true }
            )

        assertEquals(listOf<String>(), values)
        assertTrue(completed)
    }

    @Test
    fun skipMultipleSubscriptions() {
        val values1 = mutableListOf<String>()
        val values2 = mutableListOf<String>()

        val skipPublisher = Publishers.justMany("a", "b", "c")
            .skip(2)

        skipPublisher
            .subscribe(
                CancellableManager(),
                onNext = { values1 += it }
            )

        skipPublisher
            .subscribe(
                CancellableManager(),
                onNext = { values2 += it }
            )

        assertEquals(listOf("c"), values1)
        assertEquals(listOf("c"), values2)
    }
}
