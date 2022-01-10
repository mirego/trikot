package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RangePublisherTests {

    @Test
    fun rangePublisherEmitsAllValuesThenCompletes() {
        val publisher = Publishers.range(1, 5)

        val values = mutableListOf<Int>()
        var error: Throwable? = null
        var completed = false

        publisher.subscribe(
            CancellableManager(),
            onNext = { values += it },
            onError = { error = it },
            onCompleted = { completed = true }
        )

        assertEquals(listOf(1, 2, 3, 4, 5), values)
        assertNull(error)
        assertTrue(completed)
    }
}
