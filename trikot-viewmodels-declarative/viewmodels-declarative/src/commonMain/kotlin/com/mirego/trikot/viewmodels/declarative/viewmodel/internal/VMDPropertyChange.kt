package com.mirego.trikot.viewmodels.declarative.viewmodel.internal

import kotlin.reflect.KProperty

class VMDPropertyChange<V>(val property: KProperty<V>, val oldValue: V, val newValue: V)
