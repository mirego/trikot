package com.mirego.trikot.foundation.concurrent

actual class AtomicReference<T> actual constructor(value: T) {

    private val _value = kotlinx.atomicfu.atomic(value)

    actual val value: T by _value

    actual fun compareAndSet(expected: T, new: T): Boolean {
        return _value.compareAndSet(expected, new)
    }

    actual fun setOrThrow(expected: T, new: T) {
        setOrThrow(expected, new, null)
    }

    actual fun setOrThrow(expected: T, new: T, debugInfo: (() -> String)?) {
        if (!compareAndSet(expected, new)) {
            val debugInformationString = debugInfo?.let { "\n${it()}" }.orEmpty()
            throw ConcurrentModificationException(
                "($this) Unable to set $new to AtomicReference. Possible Race Condition. " +
                    "Expected value $expected was $value. +" + debugInformationString
            )
        }
    }

    actual fun compareAndSwap(expected: T, new: T): T {
        return if (compareAndSet(expected, new)) new else value
    }
}
