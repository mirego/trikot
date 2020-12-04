package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.first
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.shared
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

        publisher.first().subscribe(
            CancellableManager(),
            onNext = { value = it },
            onError = {},
            onCompleted = { completed = true }
        )

        assertEquals("a", value)
        assertTrue { completed }
    }

    @Test
    fun firstProcessorDoesNotForwardCompletionIfCompleted() {
        val initialPublisher = Publishers.behaviorSubject<String>()
        val publisher1 = initialPublisher.first()
        val publisher2 = publisher1.map { it }.first().shared()

        var result = ""
        publisher2.subscribe(CancellableManager()) {
            result += it
        }
        initialPublisher.value = "EXPECTED"
        assertEquals("EXPECTED", result)
    }
}
