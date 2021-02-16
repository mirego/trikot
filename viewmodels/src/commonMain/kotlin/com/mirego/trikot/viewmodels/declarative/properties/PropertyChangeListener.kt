package com.mirego.trikot.viewmodels.declarative.properties

import kotlin.reflect.KProperty

interface PropertyChangeListener {
    fun <V> willChange(property: KProperty<V>, oldValue: V, newValue: V)
    fun <V> didChange(property: KProperty<V>, oldValue: V, newValue: V)
}
