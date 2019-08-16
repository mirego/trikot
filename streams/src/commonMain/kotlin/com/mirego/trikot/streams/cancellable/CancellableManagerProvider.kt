package com.mirego.trikot.streams.cancellable

import com.mirego.trikot.foundation.concurrent.AtomicReference

class CancellableManagerProvider : Cancellable {
    private val cancellableManager = CancellableManager()
    private val internalCancellableManagerRef =
        AtomicReference(CancellableManager())

    fun cancelPreviousAndCreate(): CancellableManager {
        internalCancellableManagerRef.value.cancel()
        return CancellableManager().also {
            internalCancellableManagerRef.setOrThrow(internalCancellableManagerRef.value, it)
            cancellableManager.add(it)
        }
    }

    override fun cancel() {
        cancellableManager.cancel()
    }
}
