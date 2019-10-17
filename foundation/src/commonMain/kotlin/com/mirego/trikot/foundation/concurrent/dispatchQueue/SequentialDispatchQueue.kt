package com.mirego.trikot.foundation.concurrent.dispatchQueue

import com.mirego.trikot.foundation.concurrent.AtomicListReference
import com.mirego.trikot.foundation.concurrent.AtomicReference

/**
 * Ensure dispatch blocks are executed sequentially on a dispatch queue.
 */
open class SequentialDispatchQueue(override val dispatchQueue: DispatchQueue) : QueueDispatcher,
    DispatchQueue {
    private val dispatchBlockQueue = AtomicListReference<DispatchBlock>()
    private val noDispatchBlock = {}
    private val currentDispatch = AtomicReference(noDispatchBlock)

    override fun isSerial() = true

    override fun dispatch(block: DispatchBlock) {
        dispatchBlockQueue.add(block)
        startNextIfNeeded()
    }

    private fun startNextIfNeeded() {
        if (currentDispatch.value == noDispatchBlock) {
            dispatchBlockQueue.value.firstOrNull()?.let { nextDispatchBlock ->
                if (currentDispatch.compareAndSet(noDispatchBlock, nextDispatchBlock)) {
                    dispatchBlockQueue.remove(nextDispatchBlock)
                    (this as QueueDispatcher).dispatch {
                        nextDispatchBlock()
                        markDispatchBlockCompleted(nextDispatchBlock)
                        startNextIfNeeded()
                    }
                }
            }
        }
    }

    private fun markDispatchBlockCompleted(block: DispatchBlock) {
        currentDispatch.setOrThrow(block, noDispatchBlock)
    }
}
