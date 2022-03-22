package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import com.mirego.trikot.streams.reactive.scanWith
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ScanWithProcessorTests {
    @Test
    fun testCollectToList() {
        val publisher = Publishers.behaviorSubject(0)
        val receivedResults = mutableListOf<List<Int>>()
        var completed = false

        publisher
            .scanWith({ emptyList<Int>() }) { acc, current -> acc + current }
            .subscribe(
                CancellableManager(),
                onNext = { receivedResults += it },
                onError = { },
                onCompleted = { completed = true }
            )

        publisher.value = 1
        publisher.value = 2
        publisher.value = 3
        publisher.value = 4
        publisher.complete()

        val expectedResults = listOf(
            listOf(),
            listOf(0),
            listOf(0, 1),
            listOf(0, 1, 2),
            listOf(0, 1, 2, 3),
            listOf(0, 1, 2, 3, 4),
        )
        assertEquals(expectedResults, receivedResults)
        assertTrue(completed)
    }

    @Test
    fun testCollectToOtherType() {
        val publisher = Publishers.behaviorSubject(0)
        val receivedResults = mutableListOf<String>()
        var completed = false

        publisher
            .scanWith({ "" }) { acc, current -> acc + current }
            .subscribe(
                CancellableManager(),
                onNext = { receivedResults += it },
                onError = { },
                onCompleted = { completed = true }
            )

        publisher.value = 1
        publisher.value = 2
        publisher.value = 3
        publisher.value = 4
        publisher.complete()

        val expectedResults = listOf(
            "",
            "0",
            "01",
            "012",
            "0123",
            "01234"
        )
        assertEquals(expectedResults, receivedResults)
        assertTrue(completed)
    }

    @Test
    fun testCollectWithEmptyUpstream() {
        val publisher = Publishers.empty<Int>()
        val receivedResults = mutableListOf<Int>()
        var completed = false

        publisher
            .scanWith({ 0 }) { acc, current -> acc + current }
            .subscribe(
                CancellableManager(),
                onNext = { receivedResults += it },
                onError = { },
                onCompleted = { completed = true }
            )

        assertEquals(listOf(0), receivedResults)
        assertTrue(completed)
    }

    @Test
    fun testReconnection() {
        val publisher = Publishers.publishSubject<String>()
        val receivedResults = mutableListOf<String>()

        val collectPublisher = publisher.scanWith({ "" }) { acc, current -> acc + current }

        val cancellableManager1 = CancellableManager()
        val cancellableManager2 = CancellableManager()
        collectPublisher.subscribe(cancellableManager1) { receivedResults += it }

        publisher.value = "a"
        publisher.value = "b"
        publisher.value = "c"

        cancellableManager1.cancel()

        collectPublisher.subscribe(cancellableManager2) { receivedResults += it }

        publisher.value = "a"
        publisher.value = "b"
        publisher.value = "c"

        cancellableManager2.cancel()

        assertEquals(listOf("", "a", "ab", "abc", "", "a", "ab", "abc"), receivedResults)
    }

    @Test
    fun testMultipleSubscribers() {
        val publisher = Publishers.publishSubject<String>()

        val collectPublisher = publisher.scanWith({ "" }) { acc, current -> acc + current }

        val receivedResults1 = mutableListOf<String>()
        val cancellableManager1 = CancellableManager()
        collectPublisher.subscribe(cancellableManager1) { receivedResults1 += it }

        publisher.value = "a"
        publisher.value = "b"
        publisher.value = "c"

        val receivedResults2 = mutableListOf<String>()
        val cancellableManager2 = CancellableManager()
        collectPublisher.subscribe(cancellableManager2) { receivedResults2 += it }

        publisher.value = "d"
        publisher.value = "e"
        publisher.value = "f"

        assertEquals(listOf("", "a", "ab", "abc", "abcd", "abcde", "abcdef"), receivedResults1)
        assertEquals(listOf("", "d", "de", "def"), receivedResults2)
    }

    @Test
    fun testMappingStreamsProcessorException() {
        val publisher = Publishers.behaviorSubject("a")
        val expectedException = StreamsProcessorException()
        var receivedException: StreamsProcessorException? = null

        publisher
            .scanWith({ 0 }) { _, _ -> throw expectedException }
            .subscribe(
                CancellableManager(),
                onNext = { },
                onError = { receivedException = it as StreamsProcessorException }
            )

        publisher.value = "b"

        assertEquals(expectedException, receivedException)
    }

    @Test
    fun testThrowingSupplier() {
        val publisher = Publishers.behaviorSubject("a")

        var receivedException: StreamsProcessorException? = null

        val scanPublisher = publisher.scanWith({ throw IllegalStateException() }) { acc, _ -> acc }

        assertFailsWith(IllegalStateException::class) {
            scanPublisher.subscribe(
                CancellableManager(),
                onNext = { },
                onError = { receivedException = it as StreamsProcessorException }
            )
            publisher.value = "b"
        }

        assertEquals(null, receivedException)
    }

    @Test
    fun testMappingAnyException() {
        val publisher = Publishers.behaviorSubject("a")

        var receivedException: StreamsProcessorException? = null

        assertFailsWith(IllegalStateException::class) {
            publisher
                .scanWith({ 0 }) { _, _ -> throw IllegalStateException() }
                .subscribe(
                    CancellableManager(),
                    onNext = { },
                    onError = { receivedException = it as StreamsProcessorException }
                )
        }

        assertEquals(null, receivedException)
    }

    @Test
    fun testUpstreamError() {
        val error = Throwable()
        val publisher = Publishers.error<String>(error)

        var receivedException: Throwable? = null

        publisher
            .scanWith({ 0 }) { acc, value -> acc + value.toInt() }
            .subscribe(
                CancellableManager(),
                onNext = { },
                onError = { receivedException = it }
            )

        assertEquals(error, receivedException)
    }
}
