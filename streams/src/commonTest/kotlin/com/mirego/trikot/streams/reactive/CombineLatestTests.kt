package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import kotlin.test.assertNull
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.Test

class CombineLatestTests {
    @Test
    fun noValueAreDispatchedWhenMissing() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher()
        var firstValueReceived: String? = null
        var secondValueReceived: String? = null

        CombineLatest.combine2(firstPublisher, secondPublisher).subscribe(CancellableManager()) {
            firstValueReceived = it.component1
            secondValueReceived = it.component2
        }

        assertNull(firstValueReceived)
        assertNull(secondValueReceived)
    }

    @Test
    fun valuesAreDispatchedWhenAllPublishersEmits() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")
        var firstValueReceived: String? = null
        var secondValueReceived: String? = null

        CombineLatest.combine2(firstPublisher, secondPublisher).subscribe(CancellableManager()) {
            firstValueReceived = it.component1
            secondValueReceived = it.component2
        }

        assertEquals("a", firstValueReceived)
        assertEquals("b", secondValueReceived)
    }

    @Test
    fun valuesAreRedispatchedWhenAPublisherReemits() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")
        var firstValueReceived: String? = null
        var secondValueReceived: String? = null

        CombineLatest.combine2(firstPublisher, secondPublisher).subscribe(CancellableManager()) {
            firstValueReceived = it.component1
            secondValueReceived = it.component2
        }
        secondPublisher.value = "c"

        assertEquals("a", firstValueReceived)
        assertEquals("c", secondValueReceived)
    }

    @Test
    fun subscriptionsAreCancelledWhenNoOneIsConnected() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")
        val cancellableManager = CancellableManager()

        CombineLatest.combine2(firstPublisher, secondPublisher).subscribe(cancellableManager) {}
        cancellableManager.cancel()

        assertFalse { firstPublisher.getHasSubscriptions }
        assertFalse { secondPublisher.getHasSubscriptions }
    }

    @Test
    fun subscribeToChildOnlyWhenSubscriptionsAreActive() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")

        CombineLatest.combine2(firstPublisher, secondPublisher)

        assertFalse { firstPublisher.getHasSubscriptions }
        assertFalse { secondPublisher.getHasSubscriptions }
    }

    @Test
    fun whenErrorIsEmittedByOnePublisherAllSubscriptionsAreCancelled() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")
        val expectedError = Exception()
        var receivedError: Throwable? = null

        CombineLatest.combine2(firstPublisher, secondPublisher).subscribe(CancellableManager(), onNext = {}) {
            receivedError = it
        }
        secondPublisher.error = expectedError

        assertFalse { firstPublisher.getHasSubscriptions }
        assertFalse { secondPublisher.getHasSubscriptions }
        assertEquals(expectedError, receivedError)
    }

    class MockPublisher(initialValue: String? = null) : BehaviorSubjectImpl<String>(initialValue) {
        val getHasSubscriptions get() = super.hasSubscriptions
    }
}
