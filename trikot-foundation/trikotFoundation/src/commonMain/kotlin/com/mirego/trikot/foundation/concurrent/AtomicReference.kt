package com.mirego.trikot.foundation.concurrent

expect class AtomicReference<T>(value: T) {
    val value: T

    fun compareAndSet(expected: T, new: T): Boolean

    fun setOrThrow(expected: T, new: T)

    fun setOrThrow(expected: T, new: T, debugInfo: (() -> String)?)

    fun compareAndSwap(expected: T, new: T): T
}

fun <T> AtomicReference<T>.setOrThrow(new: T) {
    setOrThrow(value, new)
}
