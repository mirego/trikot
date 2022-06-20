package com.mirego.trikot.viewmodels.declarative.viewmodel.internal

import kotlin.reflect.KProperty

interface VMDPropertyChangeListener {
    fun <V> willChange(property: KProperty<V>, oldValue: V, newValue: V)
    fun <V> didChange(property: KProperty<V>, oldValue: V, newValue: V)
}
