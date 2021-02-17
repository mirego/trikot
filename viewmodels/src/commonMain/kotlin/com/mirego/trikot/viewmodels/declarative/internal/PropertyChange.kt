package com.mirego.trikot.viewmodels.declarative.internal

import kotlin.reflect.KProperty

class PropertyChange<V>(val property: KProperty<V>, val oldValue: V, val newValue: V)
