package com.mirego.trikot.viewmodels.declarative.viewmodel.internal

import kotlin.reflect.KProperty

class PropertyChange<V>(val property: KProperty<V>, val oldValue: V, val newValue: V)
