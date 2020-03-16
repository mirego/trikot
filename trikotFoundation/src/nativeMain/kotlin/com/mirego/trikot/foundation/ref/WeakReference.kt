package com.mirego.trikot.foundation.ref

actual class WeakReference<T : Any> actual constructor(value: T) {
    private val internalWeakReference = kotlin.native.ref.WeakReference(value)
    actual fun get(): T? = internalWeakReference.get()
}
