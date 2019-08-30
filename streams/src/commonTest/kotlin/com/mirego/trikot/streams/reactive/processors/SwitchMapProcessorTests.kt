package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.reactive.switchMap
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

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

    class MockPublisher : BehaviorSubjectImpl<String>("a") {
        val getHasSubscriptions get() = super.hasSubscriptions
    }
}
