package com.mirego.trikot.streams.cancellable

import com.mirego.trikot.foundation.concurrent.AtomicReference

class CancellableManagerProvider : Cancellable {
    private val internalCancellableManagerRef = AtomicReference(CancellableManager())

    fun cancelPreviousAndCreate(): CancellableManager =
        CancellableManager().also {
            internalCancellableManagerRef.getAndSet(it).cancel()
        }

    override fun cancel() {
        internalCancellableManagerRef.value.cancel()
    }
}
