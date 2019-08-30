package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.first
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FirstProcessorTests {
    @Test
    fun testFirstSendValueAndCompleted() {
        val publisher = Publishers.behaviorSubject("a")
        var value: String? = null
        var completed = false

        publisher.first().subscribe(CancellableManager(),
            onNext = { value = it },
            onError = {},
            onCompleted = { completed = true })

        assertEquals("a", value)
        assertTrue { completed }
    }
}
