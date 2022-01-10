package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import com.mirego.trikot.streams.reactive.scan
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ScanProcessorTests {
    @Test
    fun testScan() {
        val publisher = Publishers.behaviorSubject(0)
        val receivedResults = mutableListOf<Int>()
        var completed = false

        publisher
            .scan { acc, current -> acc + current }
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
        publisher.complete()

        assertEquals(listOf(0, 1, 3, 6, 10), receivedResults)
        assertTrue(completed)
    }

    @Test
    fun testScanWithInitialValue() {
        val publisher = Publishers.behaviorSubject(0)
        val receivedResults = mutableListOf<Int>()
        var completed = false

        publisher
            .scan(10) { acc, current -> acc + current }
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
        publisher.complete()

        assertEquals(listOf(10, 11, 13, 16, 20), receivedResults)
        assertTrue(completed)
    }

    @Test
    fun testReconnection() {
        val publisher = Publishers.publishSubject<String>()
        val receivedResults = mutableListOf<String>()

        val scanPublisher = publisher.scan { acc, current -> acc + current }

        val cancellableManager1 = CancellableManager()
        val cancellableManager2 = CancellableManager()
        scanPublisher
            .subscribe(cancellableManager1) {
                receivedResults.add(it)
            }

        publisher.value = "a"
        publisher.value = "b"
        publisher.value = "c"

        cancellableManager1.cancel()

        scanPublisher
            .subscribe(cancellableManager2) {
                receivedResults.add(it)
            }

        publisher.value = "a"
        publisher.value = "b"
        publisher.value = "c"

        cancellableManager2.cancel()

        assertEquals(listOf("a", "ab", "abc", "a", "ab", "abc"), receivedResults)
    }

    @Test
    fun testMappingStreamsProcessorException() {
        val publisher = Publishers.behaviorSubject("a")
        val expectedException = StreamsProcessorException()
        var receivedException: StreamsProcessorException? = null

        publisher.scan { _, _ -> throw expectedException }.subscribe(
            CancellableManager(),
            onNext = {
            },
            onError = { receivedException = it as StreamsProcessorException }
        )

        publisher.value = "b"

        assertEquals(expectedException, receivedException)
    }

    @Test
    fun testMappingAnyException() {
        val publisher = Publishers.behaviorSubject("a")

        @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
        var receivedException: StreamsProcessorException? = null

        assertFailsWith(IllegalStateException::class) {
            publisher.scan { _, _ -> throw IllegalStateException() }.subscribe(
                CancellableManager(),
                onNext = {
                },
                onError = { receivedException = it as StreamsProcessorException }
            )
            publisher.value = "b"
        }
    }
}
