package com.mirego.trikot.streams.cancellable

import com.mirego.trikot.foundation.concurrent.AtomicListReference
import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue

open class CancellableManager : Cancellable {
    private val serialQueue = SynchronousSerialQueue()
    private val queueList = AtomicListReference<Cancellable>()
    private val isCancelled = AtomicReference(false)

    open fun <T : Cancellable> add(cancellable: T): T {
        queueList.add(cancellable)
        if (queueList.value.size >= 200) {
            println("JasperCancellable: Warning size: ${queueList.value.size}")
            throw(RuntimeException("Cancellable Man!"))
        }

        if (isCancelled.value) {
            doCancelAll()
        }
        return cancellable
    }

    open fun add(cancellableBlock: () -> Unit) {
        add(
            object : Cancellable {
                override fun cancel() {
                    cancellableBlock()
                }
            }
        )
    }

    override fun cancel() {
        if (isCancelled.compareAndSet(isCancelled.value, true)) {
            doCancelAll()
        }
    }

    private fun doCancelAll() {
        serialQueue.dispatch {
            val value = queueList.value
            println("JasperCancellable: CancelAll size:  ${value.size}")
            queueList.removeAll(value)
            for (cancellable in value) {
                cancellable.cancel()
            }
        }
    }
}
