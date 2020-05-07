package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import kotlin.test.Test

class JustPublisherTests {

    @Test
    fun justPublisherEmitNextAndCompletion() {
        val publisher = Publishers.just("VALUE")
        var value = ""
        var completion = false
        publisher.subscribe(CancellableManager(),
        onNext = { value = it },
        onError = { throw IllegalStateException() },
        onCompleted = { completion = true }
        )
        kotlin.test.assertEquals("VALUE", value)
        kotlin.test.assertTrue(completion)
    }
}
