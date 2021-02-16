package com.mirego.trikot.viewmodels.declarative

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.PublishSubjectImpl
import com.mirego.trikot.streams.reactive.filter
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.viewmodels.declarative.properties.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.utilities.ConcretePublisher
import com.mirego.trikot.viewmodels.declarative.utilities.asConcretePublisher
import kotlin.reflect.KProperty
import org.reactivestreams.Publisher

open class ViewModelImpl(protected val cancellableManager: CancellableManager) : ViewModel {
    private val propertyWillChangeSubject = PublishSubjectImpl<Unit>()
    private val propertyDidChangeSubject = PublishSubjectImpl<ViewModelPropertyChange<*>>()

    override val propertyWillChange: ConcretePublisher<Unit>
        get() = propertyWillChangeSubject.asConcretePublisher()

    override val propertyDidChange: ConcretePublisher<ViewModelPropertyChange<*>>
        get() = propertyDidChangeSubject.asConcretePublisher()

    override fun <V> willChange(property: KProperty<V>, oldValue: V, newValue: V) {
        propertyWillChangeSubject.value = Unit
    }

    override fun <V> didChange(property: KProperty<V>, oldValue: V, newValue: V) {
        propertyDidChangeSubject.value = ViewModelPropertyChange(property, oldValue, newValue)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publisherForProperty(property: KProperty<V>): Publisher<V> {
        publishedProperty(property)?.let { return it.valuePublisher }

        return propertyDidChange.filter {
            it.property.name == property.name
        }.map {
            it.newValue as V
        }
    }

    protected open fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? = null

    protected fun <V> updatePropertyPublisher(
        property: KProperty<V>,
        cancellableManager: CancellableManager,
        publisher: Publisher<V>
    ) {
        publishedProperty(property)?.updatePublisher(property, publisher, cancellableManager)
    }
}
