package com.mirego.trikot.foundation.ref

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual class WeakReference<T : Any> actual constructor(value: T) {
    private val internalWeakReference = kotlin.native.ref.WeakReference(value)
    actual fun get(): T? = internalWeakReference.get()
}
