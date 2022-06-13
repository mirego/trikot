package com.mirego.trikot.viewmodels.declarative.viewmodel

import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPropertyChange
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPropertyChangeListener
import kotlin.reflect.KProperty

import kotlinx.coroutines.flow.Flow

interface VMDViewModel : VMDPropertyChangeListener, VMDContent {
    val propertyWillChange: Flow<VMDPropertyChange<*>>
    val propertyDidChange: Flow<VMDPropertyChange<*>>

    var isHidden: Boolean

    fun <V> flowForProperty(property: KProperty<V>): Flow<V>
    fun <V> flowForPropertyName(propertyName: String): Flow<V>
}
