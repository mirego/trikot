package com.mirego.trikot.streams.cancellable

import com.mirego.trikot.streams.concurrent.AtomicReference

class CancellableManagerProvider : Cancellable {
    private val cancelableManager = CancellableManager()
    private val internalCancelableManagerRef = AtomicReference(CancellableManager())

    fun cancelPreviousAndCreate(): CancellableManager {
        internalCancelableManagerRef.value.cancel()
        return CancellableManager().also {
            internalCancelableManagerRef.setOrThrow(internalCancelableManagerRef.value, it)
            cancelableManager.add(it)
        }
    }

    override fun cancel() {
        cancelableManager.cancel()
    }
}
