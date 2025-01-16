package com.mirego.trikot.streams.cancellable

import com.mirego.trikot.foundation.concurrent.AtomicListReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import kotlinx.atomicfu.atomic

class CancellableManager : Cancellable, VerifiableCancelledState {
    private val serialQueue = SynchronousSerialQueue()
    private val queueList = AtomicListReference<Cancellable>()

    private var isCancelledDelegate = atomic(false)
    override val isCancelled: Boolean by isCancelledDelegate

    fun <T : Cancellable> add(cancellable: T): T {
        queueList.add(cancellable)

        if (isCancelled) {
            doCancelAll()
        }
        return cancellable
    }

    fun add(cancellableBlock: () -> Unit) {
        add(
            object : Cancellable {
                override fun cancel() {
                    cancellableBlock()
                }
            }
        )
    }

    fun cleanCancelledChildren() {
        serialQueue.dispatch {
            val value = queueList.value
            val alreadyCancelledList = value.filter {
                when (it) {
                    is VerifiableCancelledState -> it.isCancelled
                    else -> false
                }
            }

            queueList.removeAll(alreadyCancelledList)
        }
    }

    override fun cancel() {
        if (isCancelledDelegate.compareAndSet(isCancelledDelegate.value, true)) {
            doCancelAll()
        }
    }

    private fun doCancelAll() {
        serialQueue.dispatch {
            val value = queueList.value
            queueList.removeAll(value)
            for (cancellable in value) {
                cancellable.cancel()
            }
        }
    }
}
