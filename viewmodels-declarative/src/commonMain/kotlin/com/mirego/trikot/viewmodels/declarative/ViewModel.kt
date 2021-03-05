package com.mirego.trikot.viewmodels.declarative

import com.mirego.trikot.viewmodels.declarative.internal.PropertyChange
import com.mirego.trikot.viewmodels.declarative.internal.PropertyChangeListener
import com.mirego.trikot.viewmodels.declarative.utilities.ConcretePublisher
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

interface ViewModel : PropertyChangeListener {
    val propertyWillChange: ConcretePublisher<Unit>
    val propertyDidChange: ConcretePublisher<PropertyChange<*>>

    var hidden: Boolean

    fun <V> publisherForProperty(property: KProperty<V>): Publisher<V>
}
