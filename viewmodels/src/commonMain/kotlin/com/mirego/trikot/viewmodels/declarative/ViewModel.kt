package com.mirego.trikot.viewmodels.declarative

import com.mirego.trikot.viewmodels.declarative.internal.PropertyChange
import com.mirego.trikot.viewmodels.declarative.internal.PropertyChangeListener
import com.mirego.trikot.viewmodels.declarative.properties.Content
import com.mirego.trikot.viewmodels.declarative.utilities.ConcretePublisher
import kotlin.reflect.KProperty
import org.reactivestreams.Publisher

interface ViewModel : PropertyChangeListener, Content {
    val propertyWillChange: ConcretePublisher<Unit>
    val propertyDidChange: ConcretePublisher<PropertyChange<*>>

    fun <V> publisherForProperty(property: KProperty<V>): Publisher<V>
}
