package com.mirego.trikot.datasources.testutils

import com.mirego.trikot.datasources.DataState
import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

fun <V, E : Throwable> DataState<V, E>.assertValue(expectedValue: V) =
    assertTrue { this is DataState.Data && value == expectedValue }

fun <V, E : Throwable> DataState<V, E>.assertError(expectedError: E, expectedValue: V? = null) =
    assertTrue { this is DataState.Error && error == expectedError && value == expectedValue }

fun <V, E : Throwable> DataState<V, E>.assertPending(expectedValue: V? = null) =
    assertTrue { this is DataState.Pending && value == expectedValue }

fun <T : Any> Publisher<T>.assertEquals(expectedValue: T?) {
    kotlin.test.assertEquals(expectedValue, get())
}

operator fun <T : Any> Publisher<T>.get(block: (T) -> Unit) = block(get())

fun <T : Any> Publisher<T>.get(): T {
    val atomicReference = AtomicReference<T?>(null)
    val cancellableManager = CancellableManager()
    subscribe(
        cancellableManager,
        onNext = {
            atomicReference.compareAndSet(atomicReference.value, it)
        }
    )
    cancellableManager.cancel()
    return assertNotNull(atomicReference.value, "no value returned in get")
}
