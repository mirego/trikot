package com.mirego.trikot.foundation.concurrent

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> atomic(initialValue: T) = AtomicProperty(initialValue)
fun <T : Any> atomicNullable(initialValue: T? = null) = AtomicNullableProperty(initialValue)

class AtomicProperty<T>(initialValue: T) : ReadWriteProperty<Any?, T> {
    private val internalReference = AtomicReference(initialValue)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return internalReference.value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        internalReference.setOrThrow(value)
    }
}

class AtomicNullableProperty<T : Any>(initialValue: T? = null) : ReadWriteProperty<Any?, T?> {
    private val internalReference = AtomicReference(initialValue)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return internalReference.value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        internalReference.setOrThrow(value)
    }
}
