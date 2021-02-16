package com.mirego.trikot.viewmodels.declarative

import kotlin.reflect.KProperty

class ViewModelPropertyChange<V>(val property: KProperty<V>, val oldValue: V, val newValue: V)
