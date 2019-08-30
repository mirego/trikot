package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals

class DistinctUntilChangedProcessorTests {

    @Test
    fun testProcessor() {
        val publisher = Publishers.behaviorSubject<String>()
        var executionCount = 0

        publisher.distinctUntilChanged().subscribe(CancellableManager()) {
            executionCount++
        }
        publisher.value = "a"
        publisher.value = "a"

        assertEquals(1, executionCount)
    }
}
