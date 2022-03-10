package com.mirego.trikot.viewmodels.declarative.viewmodel

import com.mirego.trikot.streams.reactive.ConcretePublisher
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPropertyChange
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPropertyChangeListener
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

interface VMDViewModel : VMDPropertyChangeListener, VMDContent {
    val propertyWillChange: ConcretePublisher<VMDPropertyChange<*>>
    val propertyDidChange: ConcretePublisher<VMDPropertyChange<*>>

    var isHidden: Boolean

    fun <V> publisherForProperty(property: KProperty<V>): Publisher<V>
    fun <V> publisherForPropertyName(propertyName: String): Publisher<V>
}
