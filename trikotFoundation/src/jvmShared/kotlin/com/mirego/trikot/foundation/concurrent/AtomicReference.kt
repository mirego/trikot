package com.mirego.trikot.foundation.concurrent

actual class AtomicReference<T> actual constructor(value: T) {
    private var internalRef = java.util.concurrent.atomic.AtomicReference(value)
    actual val value: T
        get() = internalRef.get()

    actual fun compareAndSet(expected: T, new: T): Boolean {
        return internalRef.compareAndSet(expected, new)
    }

    actual fun setOrThrow(expected: T, new: T) {
        setOrThrow(expected, new, null)
    }

    actual fun setOrThrow(expected: T, new: T, debugInfo: (() -> String)?) {
        if (!internalRef.compareAndSet(expected, new)) {
            val debugInformationString = debugInfo?.let { "\n${it()}" }
            throw ConcurrentModificationException("Unable to set $new to AtomicReference. Possible Race Condition. Expected value $expected was ${internalRef.get()}. $debugInformationString")
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
