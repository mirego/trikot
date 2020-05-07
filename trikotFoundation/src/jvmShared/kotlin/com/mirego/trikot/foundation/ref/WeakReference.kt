package com.mirego.trikot.foundation.ref

import java.lang.ref.WeakReference

actual class WeakReference<T : Any> actual constructor(value: T) {
    private var internalReference = WeakReference(value)
    actual fun get(): T? = internalReference.get()
}
