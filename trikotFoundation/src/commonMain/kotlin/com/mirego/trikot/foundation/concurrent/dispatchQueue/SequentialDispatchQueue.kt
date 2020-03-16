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
    private val currentDispatch = AtomicReference(NoDispatchBlock)

    override fun isSerial() = true

    override fun dispatch(block: DispatchBlock) {
        if (isSynchronous && currentDispatch.compareAndSet(NoDispatchBlock, SyncDispatchBlock)) {
            dispatchQueue.dispatch(block)
            currentDispatch.setOrThrow(SyncDispatchBlock, NoDispatchBlock)
            startNextIfNeeded()
        } else {
            dispatchBlockQueue.add(block)
            startNextIfNeeded()
        }
    }

    private fun startNextIfNeeded() {
        if (currentDispatch.value == NoDispatchBlock) {
            dispatchBlockQueue.value.firstOrNull()?.let { nextDispatchBlock ->
                if (currentDispatch.compareAndSet(NoDispatchBlock, nextDispatchBlock)) {
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
        currentDispatch.setOrThrow(block, NoDispatchBlock)
    }

    companion object {
        private val NoDispatchBlock = freeze {}
        private val SyncDispatchBlock = freeze {}
    }
}
