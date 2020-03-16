package com.mirego.trikot.foundation.ref

expect class WeakReference<T : Any>(value: T) {
    fun get(): T?
}
