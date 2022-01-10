package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.dispatchQueue.DispatchBlock
import com.mirego.trikot.foundation.concurrent.dispatchQueue.TrikotDispatchQueue
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.reactive.subscribeOn
import kotlin.test.Test
import kotlin.test.assertNotNull

class SubscribeOnProcessorTests {
    @Test
    fun subscribeOnQueue() {
        val dispatchQueueMock = DispatchQueueMock()
        val publisher = Publishers.behaviorSubject<String>()

        publisher.subscribeOn(dispatchQueueMock).subscribe(CancellableManager()) {}

        assertNotNull(dispatchQueueMock.block)
    }

    class DispatchQueueMock : TrikotDispatchQueue {
        var block: DispatchBlock? = null
        override fun dispatch(block: DispatchBlock) {
            this.block = block
        }
    }
}
