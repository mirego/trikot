package com.mirego.trikot.viewmodels.declarative.viewmodel

import com.mirego.trikot.streams.reactive.ConcretePublisher
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PropertyChange
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PropertyChangeListener
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

interface ViewModel : PropertyChangeListener {
    val propertyWillChange: ConcretePublisher<Unit>
    val propertyDidChange: ConcretePublisher<PropertyChange<*>>

    var hidden: Boolean

    fun <V> publisherForProperty(property: KProperty<V>): Publisher<V>
    fun <V> publisherForPropertyName(propertyName: String): Publisher<V>
}
