package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Publisher
import kotlin.test.assertNotNull

fun <T> Publisher<T>.get(block: (T) -> Unit) {
    subscribe(CancellableManager(), onNext = block)
}

fun <T> Publisher<T>.getOnce(block: (T) -> Unit) {
    first().get(block)
}

fun <T> Publisher<T>.assertEquals(expectedValue: T?) {
    var value: T? = null
    get { value = it }
    kotlin.test.assertEquals(expectedValue, value)
}

fun <T> Publisher<T>.assertTrue(block: (T?) -> Boolean) {
    var value: T? = null
    get { value = it }
    kotlin.test.assertTrue { block(value) }
}

fun <T> Publisher<T>.assertCompleted() {
    var completed = false
    subscribe(CancellableManager(), onNext = {}, onError = {}, onCompleted = { completed = true })
    assertTrue { completed }
}

fun <T> Publisher<T>.verify(value: T?, error: Throwable?, completed: Boolean) {
    var actualValue: T? = null
    var actualError: Throwable? = null
    var actualCompleted = false

    subscribe(
        CancellableManager(),
        onNext = { actualValue = it },
        onError = { actualError = it },
        onCompleted = { actualCompleted = true }
    )

    kotlin.test.assertEquals(value, actualValue)
    kotlin.test.assertEquals(error, actualError)
    kotlin.test.assertEquals(completed, actualCompleted)
}

class MockPublisher(initialValue: String? = null) : BehaviorSubjectImpl<String>(initialValue) {
    val getHasSubscriptions get() = super.hasSubscriptions
}

fun <T : Any> Publisher<T>.subscribe() {
    subscribe(CancellableManager(), onNext = {})
}

fun <T : Any> Publisher<T>.get(): T {
    var value: T? = null
    val cancellableManager = CancellableManager()
    subscribe(
        cancellableManager,
        onNext = {
            value = it
        }
    )
    cancellableManager.cancel()
    return assertNotNull(value, "no value returned in get")
}

fun <T : Any> Publisher<T>.assertError(expectedError: Throwable) {
    var error: Throwable? = null
    val cancellableManager = CancellableManager()
    subscribe(
        cancellableManager,
        {},
        {
            error = it
        }
    )
    cancellableManager.cancel()
    return kotlin.test.assertEquals(expectedError, error)
}
