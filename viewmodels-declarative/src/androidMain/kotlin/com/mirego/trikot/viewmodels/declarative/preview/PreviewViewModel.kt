package com.mirego.trikot.viewmodels.declarative.preview

import com.mirego.trikot.streams.reactive.ConcretePublisher
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.viewmodels.declarative.ViewModel
import com.mirego.trikot.viewmodels.declarative.internal.PropertyChange
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

open class PreviewViewModel(isHidden: Boolean = false) : ViewModel {
    override val propertyWillChange: ConcretePublisher<Unit> = ConcretePublisher(Publishers.publishSubject())

    override var hidden = isHidden

    override val propertyDidChange: ConcretePublisher<PropertyChange<*>> = ConcretePublisher(Publishers.publishSubject())

    override fun <V> willChange(property: KProperty<V>, oldValue: V, newValue: V) {}

    override fun <V> didChange(property: KProperty<V>, oldValue: V, newValue: V) {}

    override fun <V> publisherForProperty(property: KProperty<V>): Publisher<V> = Publishers.publishSubject()

    override fun <V> publisherForPropertyName(propertyName: String): Publisher<V> = Publishers.publishSubject()
}
