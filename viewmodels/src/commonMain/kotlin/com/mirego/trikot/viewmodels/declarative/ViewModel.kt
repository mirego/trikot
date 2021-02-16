package com.mirego.trikot.viewmodels.declarative

import com.mirego.trikot.viewmodels.declarative.properties.PropertyChangeListener
import com.mirego.trikot.viewmodels.declarative.utilities.ConcretePublisher
import kotlin.reflect.KProperty
import org.reactivestreams.Publisher

interface ViewModel : PropertyChangeListener {
    val propertyWillChange: ConcretePublisher<Unit>
    val propertyDidChange: ConcretePublisher<ViewModelPropertyChange<*>>

    fun <V> publisherForProperty(property: KProperty<V>): Publisher<V>
}
