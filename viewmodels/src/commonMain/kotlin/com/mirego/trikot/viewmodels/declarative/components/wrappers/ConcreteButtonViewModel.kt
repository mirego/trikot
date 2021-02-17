package com.mirego.trikot.viewmodels.declarative.components.wrappers

import com.mirego.trikot.viewmodels.declarative.components.ButtonViewModel
import com.mirego.trikot.viewmodels.declarative.internal.PropertyChange
import com.mirego.trikot.viewmodels.declarative.properties.Content
import com.mirego.trikot.viewmodels.declarative.utilities.ConcretePublisher
import kotlin.reflect.KProperty
import org.reactivestreams.Publisher

class ConcreteButtonViewModel<C : Content>(private val buttonViewModel: ButtonViewModel<C>) :
    ButtonViewModel<C> {
    override val content: C
        get() = buttonViewModel.content

    override val action: () -> Unit
        get() = buttonViewModel.action

    override val propertyWillChange: ConcretePublisher<Unit>
        get() = buttonViewModel.propertyWillChange

    override val propertyDidChange: ConcretePublisher<PropertyChange<*>>
        get() = buttonViewModel.propertyDidChange

    override fun <V> publisherForProperty(property: KProperty<V>): Publisher<V> =
        buttonViewModel.publisherForProperty(property)

    override fun <V> willChange(property: KProperty<V>, oldValue: V, newValue: V) {
        buttonViewModel.willChange(property, oldValue, newValue)
    }

    override fun <V> didChange(property: KProperty<V>, oldValue: V, newValue: V) {
        buttonViewModel.didChange(property, oldValue, newValue)
    }
}

fun <C : Content> ButtonViewModel<C>.wrapInConcreteClass() = ConcreteButtonViewModel(this)
