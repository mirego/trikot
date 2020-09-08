package com.mirego.trikot.foundation.concurrent

actual class AtomicReference<T> actual constructor(value: T) {
    private var internalValue: T = value
    actual val value: T
        get() = internalValue

    actual fun compareAndSet(expected: T, new: T): Boolean {
        return if (internalValue == expected) {
            internalValue = new
            true
        } else {
            false
        }
    }

    actual fun setOrThrow(expected: T, new: T) {
        setOrThrow(expected, new, null)
    }

    actual fun setOrThrow(expected: T, new: T, debugInfo: (() -> String)?) {
        compareAndSet(expected, new)
    }

    actual fun compareAndSwap(expected: T, new: T): T {
        if (internalValue == expected) {
            internalValue = new
        }
        return internalValue
    }
}
