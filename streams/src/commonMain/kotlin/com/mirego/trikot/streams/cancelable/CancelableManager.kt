package com.mirego.trikot.streams.cancelable

import com.mirego.trikot.streams.concurrent.AtomicListReference
import com.mirego.trikot.streams.concurrent.AtomicReference

class CancelableManager : Cancelable {
    private val queueList = AtomicListReference<Cancelable>()
    private val isCancelled = AtomicReference(false)

    fun <T : Cancelable> add(cancelable: T): T {
        queueList.add(cancelable)

        if (isCancelled.value) {
            doCancelAll()
        }
        return cancelable
    }

    fun add(cancelableBlock: () -> Unit) {
        add(object : Cancelable {
            override fun cancel() {
                cancelableBlock()
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
        for (cancelable in value) {
            cancelable.cancel()
        }
    }
}
