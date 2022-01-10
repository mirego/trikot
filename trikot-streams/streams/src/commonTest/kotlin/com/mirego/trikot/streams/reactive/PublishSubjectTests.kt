package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class PublishSubjectTests {
    var publishSubject = Publishers.publishSubject<String>()
    val expectedValue = "a"
    val expectedError = Exception()

    @BeforeTest
    fun setup() {
        publishSubject = Publishers.publishSubject()
    }

    @Test
    fun valueIsNotDispatchedIfValueIsSetBeforeSubscription() {
        publishSubject.value = "NO"
        var receivedValue: String? = null
        retreiveValue({ receivedValue = it })
        assertEquals(null, receivedValue)
    }

    @Test
    fun valueIsDispatchedIfValueIsSetAfterSubscription() {
        var receivedValue: String? = null
        retreiveValue({ receivedValue = it })
        publishSubject.value = expectedValue
        assertEquals(expectedValue, receivedValue)
    }

    @Test
    fun errorIsNotDispatchedIfErrorIsSetBeforeSubscription() {
        publishSubject.error = expectedError
        var receivedValue: Throwable? = null
        retreiveError({ receivedValue = it })
        assertEquals(null, receivedValue)
    }

    @Test
    fun errorIsDispatchedIfErrorIsSetAfterSubscription() {
        var receivedValue: Throwable? = null
        retreiveError({ receivedValue = it })

        publishSubject.error = expectedError
        assertEquals(expectedError, receivedValue)
    }

    @Test
    fun canCancelSubscription() {
        var receivedValue: String? = null
        val cancellableManager = CancellableManager()
        retreiveValue({ receivedValue = it }, cancellableManager)
        cancellableManager.cancel()
        publishSubject.value = "NO"
        assertEquals(null, receivedValue)
    }

    @Test
    fun canSubscriptionNotDoneIfAlreadyCancelled() {
        var receivedValue: String? = null
        val cancellableManager = CancellableManager().also { it.cancel() }
        retreiveValue({ receivedValue = it }, cancellableManager)
        publishSubject.value = "NO"
        assertEquals(null, receivedValue)
    }

    @Test
    fun canComplete() {
        var completed = false
        retreiveCompleted({ completed = true })
        publishSubject.complete()
        assertTrue { completed }
    }

    @Test
    fun cannotDispatchAfterCompletion() {
        publishSubject.complete()

        assertFailsWith(IllegalStateException::class) {
            publishSubject.value = "a"
        }
    }

    @Test
    fun subscriberIsNotAddedToListWhenIsCancellationOccursInNewSubscriptionCallback() {
        val publisher1 = PublishSubjectMock<String>()
        val publisher2 = PublishSubjectMock<String>()
        var completed = false
        publisher1
            .switchMap { publisher2 }
            .first()
            .subscribe(
                CancellableManager(),
                onNext = {
                },
                onError = {},
                onCompleted = { completed = true }
            )
        publisher1.value = "1"
        publisher2.value = "2"

        assertTrue { completed }
        assertTrue { publisher1.hasNoSubscription }
        assertTrue { publisher2.hasNoSubscription }
    }

    class PublishSubjectMock<T>() : PublishSubjectImpl<T>() {
        val hasNoSubscription get() = !super.hasSubscriptions
    }

    fun retreiveValue(block: (String) -> Unit, cancellableManager: CancellableManager = CancellableManager()) {
        publishSubject.first().subscribe(cancellableManager, block)
    }

    fun retreiveError(block: (Throwable) -> Unit, cancellableManager: CancellableManager = CancellableManager()) {
        publishSubject.first().subscribe(cancellableManager, onNext = {}, onError = block)
    }

    fun retreiveCompleted(block: () -> Unit, cancellableManager: CancellableManager = CancellableManager()) {
        publishSubject.first().subscribe(cancellableManager, onNext = {}, onError = {}, onCompleted = block)
    }
}
