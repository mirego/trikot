package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Publisher

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

class MockPublisher(initialValue: String? = null) : BehaviorSubjectImpl<String>(initialValue) {
    val getHasSubscriptions get() = super.hasSubscriptions
}
