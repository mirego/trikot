package com.mirego.trikot.streams.cancellable

import com.mirego.trikot.foundation.concurrent.AtomicListReference
import com.mirego.trikot.foundation.concurrent.AtomicReference

class CancellableManager : Cancellable {
    private val queueList = AtomicListReference<Cancellable>()
    private val isCancelled = AtomicReference(false)

    fun <T : Cancellable> add(cancellable: T): T {
        queueList.add(cancellable)

        if (isCancelled.value) {
            doCancelAll()
        }
        return cancellable
    }

    fun add(cancellableBlock: () -> Unit) {
        add(object : Cancellable {
            override fun cancel() {
                cancellableBlock()
            }
        })
    }

    override fun cancel() {
        isCancelled.setOrThrow(isCancelled.value, true)
        doCancelAll()
    }

    private fun doCancelAll() {
        val value = queueList.value
        queueList.removeAll(value)
        for (cancellable in value) {
            cancellable.cancel()
        }
    }
}
