package com.mirego.trikot.kword

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

fun <T : Any> Publisher<T>.get(block: (T) -> Unit) = block(get())

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

fun <T : Any> Publisher<T>.assertEquals(expectedValue: T?) {
    assertEquals(expectedValue, get())
}
