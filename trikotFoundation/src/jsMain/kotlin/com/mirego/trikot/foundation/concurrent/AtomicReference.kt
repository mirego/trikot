package com.mirego.trikot.foundation.concurrent

actual class AtomicReference<T> actual constructor(value: T) {
    private var internalValue: T = value
    actual val value: T
        get() = internalValue

    actual fun compareAndSet(expected: T, new: T): Boolean {
        internalValue = new
        return true
    }

    actual fun setOrThrow(expected: T, new: T) {
        setOrThrow(expected, new, null)
    }

    actual fun setOrThrow(expected: T, new: T, debugInfo: (() -> String)?) {
        compareAndSet(expected, new)
    }

    actual fun compareAndSwap(expected: T, new: T): T {
        internalValue = new
        return internalValue
    }
}
