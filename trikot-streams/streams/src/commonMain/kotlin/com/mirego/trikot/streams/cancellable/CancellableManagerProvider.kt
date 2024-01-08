package com.mirego.trikot.streams.cancellable

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue

class CancellableManagerProvider : Cancellable {
    private val serialQueue = SynchronousSerialQueue()
    private val internalCancellableManagerRef = AtomicReference(CancellableManager())
    private val isCancelled = AtomicReference(false)

    fun cancelPreviousAndCreate(): CancellableManager {
        return CancellableManager().also { cancellableManager ->
            internalCancellableManagerRef.getAndSet(cancellableManager).cancel()
            serialQueue.dispatch {
                if (isCancelled.value) {
                    internalCancellableManagerRef.value.cancel()
                }
            }
        }
    }

    override fun cancel() {
        serialQueue.dispatch {
            if (isCancelled.compareAndSet(isCancelled.value, true)) {
                internalCancellableManagerRef.value.cancel()
            }
        }
    }
}
