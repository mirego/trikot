package com.mirego.trikot.foundation.concurrent

import kotlin.native.concurrent.freeze
import kotlin.native.concurrent.isFrozen

actual class AtomicReference<T> actual constructor(value: T) {
    private val nativeAtomicReference = kotlin.native.concurrent.AtomicReference<T?>(null)
    private var expectedUnfrozenValue: T = value
    actual val value: T get() = nativeAtomicReference.value ?: expectedUnfrozenValue

    actual fun compareAndSet(expected: T, new: T): Boolean {
        return if (isFrozen) {
            val freezedNewValue = new.freeze()
            if (nativeAtomicReference.compareAndSet(expected, freezedNewValue)) {
                true
            } else {
                (expected === expectedUnfrozenValue) && nativeAtomicReference.compareAndSet(null, freezedNewValue)
            }
        } else {
            if (expected === expectedUnfrozenValue) {
                expectedUnfrozenValue = new
                true
            } else {
                false
            }
        }
    }

    actual fun setOrThrow(expected: T, new: T) {
        setOrThrow(expected, new, null)
    }

    actual fun setOrThrow(expected: T, new: T, debugInfo: (() -> String)?) {
        if (!compareAndSet(expected, new)) {
            val debugInformationString = debugInfo?.let { "\n${it()}" }
            throw ConcurrentModificationException("($this) Unable to set $new to AtomicReference. Possible Race Condition. Expected value $expected was $value (Unfrozen: $expectedUnfrozenValue). (Internal: ${nativeAtomicReference.value}) Is class frozen: $isFrozen. $debugInformationString")
        }
    }

    actual fun compareAndSwap(expected: T, new: T): T {
        return if (compareAndSet(expected, new)) new else expected
    }
}
