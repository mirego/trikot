package com.mirego.trikot.foundation.concurrent

import kotlin.native.concurrent.freeze
import kotlin.native.concurrent.isFrozen

actual class AtomicReference<T> actual constructor(value: T) {
    private val nativeAtomicReference = kotlin.native.concurrent.AtomicReference<T?>(null)
    private var unfrozenValue: T = value
    actual val value: T get() = nativeAtomicReference.value ?: unfrozenValue

    actual fun compareAndSet(expected: T, new: T): Boolean {
        return if (isFrozen) {
            val freezedNewValue = new.freeze()
            nativeAtomicReference.compareAndSet(expected, freezedNewValue) ||
                    nativeAtomicReference.compareAndSet(null, freezedNewValue)
        } else {
            if (expected === unfrozenValue) {
                unfrozenValue = new
                true
            } else {
                false
            }
        }
    }

    actual fun setOrThrow(expected: T, new: T) {
        if (!compareAndSet(expected, new)) {
            throw ConcurrentModificationException("($this) Unable to set $new to AtomicReference. Possible Race Condition. Expected value $expected was $value (Unfrozen: $unfrozenValue). (Internal: ${nativeAtomicReference.value}) Is class frozen: $isFrozen")
        }
    }

    actual fun compareAndSwap(expected: T, new: T): T {
        return if (compareAndSet(expected, new)) new else expected
    }
}
