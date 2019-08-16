package com.mirego.trikot.streams.attachable

import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.foundation.concurrent.AtomicReference

abstract class AbstractAttachable(private val maxSimultaneousAttachCount: Int = Int.MAX_VALUE) : Attachable {
    private val attachCountRef = AtomicReference(0)
    private val cancellableManagerProvider = CancellableManagerProvider()

    override fun attach(): Cancellable {
        var attachCount = attachCountRef.value
        attachCount += 1
        attachCountRef.setOrThrow(attachCount - 1, attachCount)

        if (attachCount > maxSimultaneousAttachCount) throw IllegalStateException("attach() can only be called $maxSimultaneousAttachCount time(s) on this instance")

        if (attachCount == 1) {
            doAttach(cancellableManagerProvider.cancelPreviousAndCreate())
        }
        return object : Cancellable {
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
            cancellableManagerProvider.cancelPreviousAndCreate()
        }
    }

    protected open fun doAttach(cancellableManager: CancellableManager) {
    }

    protected open fun doDetach() {
    }
}
