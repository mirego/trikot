package com.mirego.trikot.foundation.ref

actual class WeakReference<T : Any> actual constructor(private val value: T) {
    actual fun get(): T? = value
}
