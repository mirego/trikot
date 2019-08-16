package com.mirego.trikot.foundation.concurrent

actual class AtomicReference<T> actual constructor(value: T) {
    private var internalRef = java.util.concurrent.atomic.AtomicReference(value)
    actual val value: T
        get() = internalRef.get()

    actual fun compareAndSet(expected: T, new: T): Boolean {
        return internalRef.compareAndSet(expected, new)
    }

    actual fun setOrThrow(expected: T, new: T) {
        if (!internalRef.compareAndSet(expected, new)) {
            throw ConcurrentModificationException("Unable to set the new value to AtomicReference. Possible Race Condition. Expected value $expected was ${internalRef.get()}")
        }
    }

    actual fun compareAndSwap(expected: T, new: T): T {
        return if (compareAndSet(expected, new)) {
            new
        } else {
            expected
        }
    }
}
