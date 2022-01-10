package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BehaviorSubjectTests {
    var behaviorSubject = Publishers.behaviorSubject<String>()
    val expectedValue = "a"
    val expectedError = Exception()

    @BeforeTest
    fun setup() {
        behaviorSubject = Publishers.behaviorSubject()
    }

    @Test
    fun valueIsNotDispatchedIfValueIsSetBeforeSubscription() {
        behaviorSubject.value = expectedValue
        var receivedValue: String? = null
        retreiveValue({ receivedValue = it })
        assertEquals(expectedValue, receivedValue)
    }

    @Test
    fun errorIsNotDispatchedIfErrorIsSetBeforeSubscription() {
        behaviorSubject.error = expectedError
        var receivedValue: Throwable? = null
        retreiveError({ receivedValue = it })
        assertEquals(expectedError, receivedValue)
    }

    fun retreiveValue(block: (String) -> Unit, cancellableManager: CancellableManager = CancellableManager()) {
        behaviorSubject.first().subscribe(cancellableManager, block)
    }

    fun retreiveError(block: (Throwable) -> Unit, cancellableManager: CancellableManager = CancellableManager()) {
        behaviorSubject.first().subscribe(cancellableManager, onNext = {}, onError = block)
    }
}
