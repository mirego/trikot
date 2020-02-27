package com.mirego.trikot.foundation.ref

import com.mirego.trikot.foundation.concurrent.AtomicReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Creates weak atomic reference property delegate.
 * It can only be used in initialize of read-write optional property, like this:
 *
 * ```
 * val weakType : Type? by weakReference()
 * ```
 */
fun <T : Any> weakAtomicReference() =
    WeakProperty<T>()

/**
 * Weak atomic reference to a variable of type [T].
 */
class WeakProperty<T : Any> : ReadWriteProperty<Any?, T?> {
    private val internalReference = AtomicReference<WeakReference<T>?>(null)
    private val delegate: T? get() = internalReference.value?.get()

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return delegate
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        internalReference.setOrThrow(
            internalReference.value,
            value?.let { WeakReference(it) }
        )
    }
}
