package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.MockPublisher
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.reactive.switchMap
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SwitchMapProcessorTests {
    @Test
    fun switchMap() {
        val switchMappedPublisher = Publishers.behaviorSubject("a")
        val returnedPublisher = Publishers.behaviorSubject("b")
        var receivedValueSwitchMap: String? = null
        var receivedValueSubscription: String? = null

        switchMappedPublisher.switchMap {
            receivedValueSwitchMap = it
            returnedPublisher
        }.subscribe(CancellableManager()) {
            receivedValueSubscription = it
        }

        assertEquals("a", receivedValueSwitchMap)
        assertEquals("b", receivedValueSubscription)
    }

    @Test
    fun switchMapDisconnectFromPreviousPublisher() {
        val switchMappedPublisher = Publishers.behaviorSubject("a")
        val returnedPublisher = MockPublisher()
        val cancellableManager = CancellableManager()

        switchMappedPublisher.switchMap { returnedPublisher
        }.subscribe(cancellableManager) {
        }

        cancellableManager.cancel()

        assertFalse { returnedPublisher.getHasSubscriptions }
    }

    @Test
    fun whenSwitchMappedIsCompletedAndProvidedNotCompletedThenNotCompleted() {
        val switchMappedPublisher = Publishers.behaviorSubject("a")
        val returnedPublisher = Publishers.behaviorSubject("b")
        var isCompleted = false

        switchMappedPublisher.switchMap { returnedPublisher }.subscribe(CancellableManager(), onNext = {}, onError = {}, onCompleted = { isCompleted = true })
        switchMappedPublisher.complete()
        assertFalse { isCompleted }
    }

    @Test
    fun whenSwitchMappedIsNotCompletedAndProvidedIsCompletedThenNotCompleted() {
        val switchMappedPublisher = Publishers.behaviorSubject("a")
        val returnedPublisher = Publishers.behaviorSubject("b")
        var isCompleted = false

        switchMappedPublisher.switchMap { returnedPublisher }.subscribe(CancellableManager(), onNext = {}, onError = {}, onCompleted = { isCompleted = true })
        returnedPublisher.complete()
        assertFalse { isCompleted }
    }

    @Test
    fun whenSwitchMappedIsCompletedAndProvidedIsCompletedThenCompleted() {
        val switchMappedPublisher = Publishers.behaviorSubject("a")
        val returnedPublisher = Publishers.behaviorSubject("b")
        var isCompleted = false

        switchMappedPublisher.switchMap { returnedPublisher }.subscribe(CancellableManager(), onNext = {}, onError = {}, onCompleted = { isCompleted = true })
        switchMappedPublisher.complete()
        returnedPublisher.complete()
        assertTrue { isCompleted }
    }

    @Test
    fun testMappingStreamsProcessorException() {
        val publisher = Publishers.behaviorSubject("a")
        val expectedException = StreamsProcessorException()
        var receivedException: StreamsProcessorException? = null

        publisher.switchMap<String, String> { throw expectedException }.subscribe(CancellableManager(), onNext = {
        }, onError = { receivedException = it as StreamsProcessorException })

        assertEquals(expectedException, receivedException)
    }

    @Test
    fun testMappingAnyException() {
        val publisher = Publishers.behaviorSubject("a")
        var receivedException: StreamsProcessorException? = null

        assertFailsWith(IllegalStateException::class) {
            publisher.switchMap<String, String> { throw IllegalStateException() }.subscribe(CancellableManager(), onNext = {
            }, onError = { receivedException = it as StreamsProcessorException })
        }

        assertEquals(null, receivedException)
    }

    @Test
    fun testChildIsDetachedAfterAnError() {
        val publisher = Publishers.behaviorSubject("a")
        val childPublisher = Publishers.behaviorSubject("a").also { it.error = Throwable() }

        var errorCallCount = 0
        publisher.switchMap { childPublisher }.subscribe(CancellableManager(), {}, { errorCallCount ++ })
        publisher.value = "b"

        assertEquals(1, errorCallCount)
    }
}
