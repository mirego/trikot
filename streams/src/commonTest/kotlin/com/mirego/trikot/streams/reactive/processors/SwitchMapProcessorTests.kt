package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.reactive.switchMap
import kotlin.test.Test
import kotlin.test.assertEquals
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

        switchMappedPublisher.switchMap { returnedPublisher }.subscribe(CancellableManager(), onNext = {}, onError = {}, onCompleted = {isCompleted = true})
        switchMappedPublisher.complete()
        assertFalse { isCompleted }
    }

    @Test
    fun whenSwitchMappedIsNotCompletedAndProvidedIsCompletedThenNotCompleted() {
        val switchMappedPublisher = Publishers.behaviorSubject("a")
        val returnedPublisher = Publishers.behaviorSubject("b")
        var isCompleted = false

        switchMappedPublisher.switchMap { returnedPublisher }.subscribe(CancellableManager(), onNext = {}, onError = {}, onCompleted = {isCompleted = true})
        returnedPublisher.complete()
        assertFalse { isCompleted }
    }

    @Test
    fun whenSwitchMappedIsCompletedAndProvidedIsCompletedThenCompleted() {
        val switchMappedPublisher = Publishers.behaviorSubject("a")
        val returnedPublisher = Publishers.behaviorSubject("b")
        var isCompleted = false

        switchMappedPublisher.switchMap { returnedPublisher }.subscribe(CancellableManager(), onNext = {}, onError = {}, onCompleted = {isCompleted = true})
        switchMappedPublisher.complete()
        returnedPublisher.complete()
        assertTrue { isCompleted }
    }

    class MockPublisher : BehaviorSubjectImpl<String>("a") {
        val getHasSubscriptions get() = super.hasSubscriptions
    }
}
