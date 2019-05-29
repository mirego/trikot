package com.mirego.trikot.streams.attachable

import com.mirego.trikot.streams.cancelable.Cancelable
import com.mirego.trikot.streams.cancelable.CancelableManager
import com.mirego.trikot.streams.cancelable.ResettableCancelableManager
import com.mirego.trikot.streams.concurrent.AtomicReference

abstract class AbstractAttachable(private val maxSimultaneousAttachCount: Int = Int.MAX_VALUE) : Attachable {
    private val attachCountRef = AtomicReference(0)
    private val resetableCancelableManager = ResettableCancelableManager()

    override fun attach(): Cancelable {
        var attachCount = attachCountRef.value
        attachCount += 1
        attachCountRef.setOrThrow(attachCount - 1, attachCount)

        if (attachCount > maxSimultaneousAttachCount) throw IllegalStateException("attach() can only be called $maxSimultaneousAttachCount time(s) on this instance")

        if (attachCount == 1) {
            doAttach(resetableCancelableManager.reset())
        }
        return object : Cancelable {
            var isDetached = AtomicReference(false)
            override fun cancel() {
                if (isDetached.compareAndSet(false, true)) {
                    detach()
                }
            }
        }
    }

    private fun detach() {
        val attachCount = attachCountRef.value
        val newattachCount = attachCount - 1
        attachCountRef.setOrThrow(attachCount, newattachCount)

        if (newattachCount == 0) {
            doDetach()
            resetableCancelableManager.cancel()
        }
    }

    protected open fun doAttach(cancelableManager: CancelableManager) {
    }

    protected open fun doDetach() {
    }
}
