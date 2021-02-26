package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.dispatchQueue.DispatchBlock
import com.mirego.trikot.foundation.concurrent.dispatchQueue.TrikotDispatchQueue
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.observeOn
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertNotNull

class ObserveOnProcessorTests {
    @Test
    fun valueTest() {
        val dispatchQueue = DispatchQueueMock()
        val publisher = Publishers.behaviorSubject("a")

        val observeOn = publisher.observeOn(dispatchQueue)
        observeOn.subscribe(CancellableManager()) {}

        assertNotNull(dispatchQueue.block)
    }

    @Test
    fun errorTest() {
        val dispatchQueue = DispatchQueueMock()
        val publisher = Publishers.behaviorSubject<String>()

        publisher.error = Exception()
        publisher.observeOn(dispatchQueue).subscribe(CancellableManager()) {}

        assertNotNull(dispatchQueue.block)
    }

    @Test
    fun completedTest() {
        val dispatchQueue = DispatchQueueMock()
        val publisher = Publishers.behaviorSubject<String>()

        publisher.complete()
        publisher.observeOn(dispatchQueue).subscribe(CancellableManager()) {}

        assertNotNull(dispatchQueue.block)
    }

    class DispatchQueueMock : TrikotDispatchQueue {
        var block: DispatchBlock? = null
        override fun dispatch(block: DispatchBlock) {
            this.block = block
        }
    }
}
