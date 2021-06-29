package com.mirego.sample.viewmodels.preview

import com.mirego.trikot.streams.reactive.ConcretePublisher
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.asConcretePublisher
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PropertyChange
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

open class ViewModelPreview(override var hidden: Boolean = false) : ViewModel {
    override val propertyWillChange: ConcretePublisher<Unit>
        get() = Publishers.publishSubject<Unit>().asConcretePublisher()

    override val propertyDidChange: ConcretePublisher<PropertyChange<*>>
        get() = Publishers.publishSubject<PropertyChange<*>>().asConcretePublisher()

    override fun <V> publisherForProperty(property: KProperty<V>): Publisher<V> {
        return Publishers.publishSubject()
    }

    override fun <V> publisherForPropertyName(propertyName: String): Publisher<V> {
        return Publishers.publishSubject()
    }

    override fun <V> willChange(property: KProperty<V>, oldValue: V, newValue: V) {
        // Do nothing
    }

    override fun <V> didChange(property: KProperty<V>, oldValue: V, newValue: V) {
        // Do nothing
    }
}
