package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.reactive.takeUntil
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TakeUntilProcessorTests {

    @Test
    fun testTakeUntil() {
        val publisher = Publishers.behaviorSubject(0)
        val receivedResults = mutableListOf<Int>()
        var completed = false

        publisher
            .takeUntil { it == 3 }
            .subscribe(
                CancellableManager(),
                onNext = {
                    receivedResults.add(it)
                },
                onError = {
                },
                onCompleted = {
                    completed = true
                }
            )

        publisher.value = 1
        publisher.value = 2
        publisher.value = 3
        publisher.value = 4

        assertEquals(listOf(0, 1, 2, 3), receivedResults)
        assertTrue(completed)
    }

    @Test
    fun testCompletionBehaviorWhenPredicateIsTrueAndSourcePublisherIsCompleted() {
        val publisher = Publishers.just(true)
        val receivedResults = mutableListOf<Boolean>()
        var completed = false

        publisher
            .takeUntil { it }
            .subscribe(
                CancellableManager(),
                onNext = {
                    receivedResults.add(it)
                },
                onError = {
                },
                onCompleted = {
                    completed = true
                }
            )

        assertEquals(listOf(true), receivedResults)
        assertTrue(completed)
    }

    @Test
    fun testCompletionBehaviorWhenPredicateIsFalseAndSourcePublisherIsCompleted() {
        val publisher = Publishers.just(false)
        val receivedResults = mutableListOf<Boolean>()
        var completed = false

        publisher
            .takeUntil { it }
            .subscribe(
                CancellableManager(),
                onNext = {
                    receivedResults.add(it)
                },
                onError = {
                },
                onCompleted = {
                    completed = true
                }
            )

        assertEquals(listOf(false), receivedResults)
        assertTrue(completed)
    }

    @Test
    fun testReconnectionWithTruePredicate() {
        val publisher = Publishers.publishSubject<String>()
        val receivedResults = mutableListOf<String>()

        val takeUntilPublisher = publisher.takeUntil { it == "c" }

        val cancellableManager1 = CancellableManager()
        val cancellableManager2 = CancellableManager()

        takeUntilPublisher
            .subscribe(cancellableManager1) {
                receivedResults.add(it)
            }

        publisher.value = "a"
        publisher.value = "b"
        publisher.value = "c"
        publisher.value = "d"

        takeUntilPublisher
            .subscribe(cancellableManager2) {
                receivedResults.add(it)
            }

        publisher.value = "a"
        publisher.value = "b"
        publisher.value = "c"
        publisher.value = "d"

        assertEquals(listOf("a", "b", "c", "a", "b", "c"), receivedResults)
    }

    @Test
    fun testReconnectionWithoutTruePredicate() {
        val publisher = Publishers.publishSubject<String>()
        val receivedResults = mutableListOf<String>()

        val takeUntilPublisher = publisher.takeUntil { it == "d" }

        val cancellableManager1 = CancellableManager()
        val cancellableManager2 = CancellableManager()

        takeUntilPublisher
            .subscribe(cancellableManager1) {
                receivedResults.add(it)
            }

        publisher.value = "a"
        publisher.value = "b"
        publisher.value = "c"

        cancellableManager1.cancel()

        takeUntilPublisher
            .subscribe(cancellableManager2) {
                receivedResults.add(it)
            }

        publisher.value = "a"
        publisher.value = "b"
        publisher.value = "c"

        cancellableManager2.cancel()

        assertEquals(listOf("a", "b", "c", "a", "b", "c"), receivedResults)
    }

    @Test
    fun testShouldCompleteIfPredicateIsTrue() {
        val publisher = Publishers.publishSubject<String>()
        val receivedResults = mutableListOf<String>()

        val takeUntilPublisher = publisher.takeUntil { it == "b" }

        var firstCompletion = false
        var secondCompletion = false

        takeUntilPublisher
            .subscribe(
                CancellableManager(),
                onNext = {
                    receivedResults.add(it)
                },
                onError = {
                },
                onCompleted = {
                    firstCompletion = true
                }
            )

        publisher.value = "a"
        publisher.value = "b"

        takeUntilPublisher
            .subscribe(
                CancellableManager(),
                onNext = {
                    receivedResults.add(it)
                },
                onError = {
                },
                onCompleted = {
                    secondCompletion = true
                }
            )

        publisher.value = "a"
        publisher.value = "b"

        assertTrue(firstCompletion)
        assertTrue(secondCompletion)
    }

    @Test
    fun testMappingStreamsProcessorException() {
        val publisher = Publishers.behaviorSubject("a")
        val expectedException = StreamsProcessorException()
        var receivedException: StreamsProcessorException? = null

        publisher.takeUntil { throw expectedException }
            .subscribe(
                CancellableManager(),
                onNext = {
                },
                onError = {
                    receivedException = it as StreamsProcessorException
                }
            )

        assertEquals(expectedException, receivedException)
    }

    @Test
    fun testMappingAnyException() {
        val publisher = Publishers.behaviorSubject("a")

        assertFailsWith(IllegalStateException::class) {
            publisher.takeUntil { throw IllegalStateException() }.subscribe(CancellableManager()) {}
        }
    }
}
