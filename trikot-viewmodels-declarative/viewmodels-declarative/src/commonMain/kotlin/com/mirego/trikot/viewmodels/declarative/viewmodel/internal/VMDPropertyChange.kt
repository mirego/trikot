package com.mirego.trikot.viewmodels.declarative.viewmodel.internal

import com.mirego.trikot.viewmodels.declarative.components.animation.VMDAnimation
import kotlin.reflect.KProperty

class VMDPropertyChange<V>(val property: KProperty<V>, val oldValue: V, val newValue: V, val animation: VMDAnimation? = null)
