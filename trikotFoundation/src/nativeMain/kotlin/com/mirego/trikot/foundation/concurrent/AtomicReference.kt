package com.mirego.trikot.foundation.concurrent

import kotlin.native.concurrent.freeze
import kotlin.native.concurrent.isFrozen

actual class AtomicReference<T> actual constructor(value: T) {
    private val nativeAtomicReference = kotlin.native.concurrent.AtomicReference<T?>(null)
    private val shouldReadFromNativeAtomicReference =
        kotlin.native.concurrent.AtomicReference(false)
    private var expectedUnfrozenValue: T = value

    @Suppress("UNCHECKED_CAST")
    actual val value: T
        get() = if (shouldReadFromNativeAtomicReference.value)
            nativeAtomicReference.value as T
        else
            expectedUnfrozenValue

    actual fun compareAndSet(expected: T, new: T): Boolean {
        return if (isFrozen) {
            val frozenNewValue = new.freeze()
            if (nativeAtomicReference.compareAndSet(expected, frozenNewValue)) {
                shouldReadFromNativeAtomicReference.compareAndSet(false, true)
                true
            } else {
                if ((expected === expectedUnfrozenValue) && nativeAtomicReference.compareAndSet(
                        null,
                        frozenNewValue
                    )
                ) {
                    shouldReadFromNativeAtomicReference.compareAndSet(false, true)
                    true
                } else {
                    false
                }
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
            throw ConcurrentModificationException("($this) Unable to set $new to AtomicReference. Possible Race Condition. Expected value $expected was $value (Unfrozen: $expectedUnfrozenValue). (Internal: ${nativeAtomicReference.value}) Is class frozen: $isFrozen. ${debugInformationString ?: ""}")
        }
    }

    actual fun compareAndSwap(expected: T, new: T): T {
        return if (compareAndSet(expected, new)) new else value
    }
}
