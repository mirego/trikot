package com.mirego.trikot.foundation.concurrent.dispatchQueue

import com.mirego.trikot.foundation.concurrent.AtomicListReference
import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.freeze

/**
 * Ensure dispatch blocks are executed sequentially on a dispatch queue.
 */
open class SequentialDispatchQueue(override val dispatchQueue: DispatchQueue) : QueueDispatcher,
    DispatchQueue {
    protected open val isSynchronous = false
    private val dispatchBlockQueue = AtomicListReference<DispatchBlock>()
    private val noDispatchBlock = freeze {}
    private val syncDispatchBlock = freeze {}
    private val currentDispatch = AtomicReference(noDispatchBlock)

    override fun isSerial() = true

    override fun dispatch(block: DispatchBlock) {
        if (isSynchronous && currentDispatch.compareAndSet(noDispatchBlock, syncDispatchBlock)) {
            dispatchQueue.dispatch(block)
            currentDispatch.setOrThrow(syncDispatchBlock, noDispatchBlock)
            startNextIfNeeded()
        } else {
            dispatchBlockQueue.add(freeze(block))
            startNextIfNeeded()
        }
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
