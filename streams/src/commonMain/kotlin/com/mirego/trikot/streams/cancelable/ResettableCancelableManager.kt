package com.mirego.trikot.streams.cancelable

import com.mirego.trikot.streams.concurrent.AtomicReference

class ResettableCancelableManager : Cancelable {
    private val internalCancelableManagerRef = AtomicReference(CancelableManager())

    fun reset(): CancelableManager {
        cancel()
        val cancelableManager = CancelableManager()
        internalCancelableManagerRef.setOrThrow(internalCancelableManagerRef.value, cancelableManager)
        return cancelableManager
    }

    fun add(cancelable: Cancelable) {
        internalCancelableManagerRef.value.add(cancelable)
    }

    override fun cancel() {
        internalCancelableManagerRef.value.cancel()
    }
}
