package com.mirego.trikot.foundation.concurrent

class AtomicReference<T>(value: T) {

    private val atomicValue = kotlinx.atomicfu.atomic(value)

    val value: T get() = atomicValue.value

    fun compareAndSet(expected: T, new: T): Boolean = atomicValue.compareAndSet(expected, new)

    fun setOrThrow(expected: T, new: T) = setOrThrow(expected, new, null)

    fun setOrThrow(expected: T, new: T, debugInfo: (() -> String)?) {
        if (!compareAndSet(expected, new)) {
            throw ConcurrentModificationException(
                "($this) Unable to set $new to AtomicReference. " +
                    "Possible Race Condition. Expected value $expected was $value. " +
                    debugInfo?.let { "\n${it()}" }.orEmpty()
            )
        }
    }

    fun compareAndSwap(expected: T, new: T): T {
        return if (compareAndSet(expected, new)) new else value
    }

    fun getAndSet(new: T): T {
        return atomicValue.getAndSet(new)
    }
}

fun <T> AtomicReference<T>.setOrThrow(new: T) = setOrThrow(value, new)
