package com.mirego.trikot.streams.cancellable

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import kotlinx.atomicfu.atomic

class CancellableManagerProvider : Cancellable, VerifiableCancelledState {
    private val serialQueue = SynchronousSerialQueue()
    private val internalCancellableManagerRef = AtomicReference(CancellableManager())

    private val isCancelledDelegate = atomic(false)
    override val isCancelled: Boolean by isCancelledDelegate

    fun cancelPreviousAndCreate(): CancellableManager {
        return CancellableManager().also { cancellableManager ->
            internalCancellableManagerRef.getAndSet(cancellableManager).cancel()
            serialQueue.dispatch {
                if (isCancelled) {
                    cancellableManager.cancel()
                }
            }
        }
    }

    override fun cancel() {
        serialQueue.dispatch {
            if (isCancelledDelegate.compareAndSet(isCancelledDelegate.value, true)) {
                internalCancellableManagerRef.value.cancel()
            }
        }
    }
}
