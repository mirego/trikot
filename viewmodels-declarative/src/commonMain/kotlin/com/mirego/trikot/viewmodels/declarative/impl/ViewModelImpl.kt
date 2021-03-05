package com.mirego.trikot.viewmodels.declarative.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.PublishSubjectImpl
import com.mirego.trikot.streams.reactive.filter
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.viewmodels.declarative.ViewModel
import com.mirego.trikot.viewmodels.declarative.internal.PropertyChange
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import com.mirego.trikot.viewmodels.declarative.utilities.ConcretePublisher
import com.mirego.trikot.viewmodels.declarative.utilities.asConcretePublisher
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

open class ViewModelImpl(protected val cancellableManager: CancellableManager) : ViewModel {

    private val propertyWillChangeSubject = PublishSubjectImpl<Unit>()
    private val propertyDidChangeSubject = PublishSubjectImpl<PropertyChange<*>>()

    override val propertyWillChange: ConcretePublisher<Unit>
        get() = propertyWillChangeSubject.asConcretePublisher()

    override val propertyDidChange: ConcretePublisher<PropertyChange<*>>
        get() = propertyDidChangeSubject.asConcretePublisher()

    override fun <V> willChange(property: KProperty<V>, oldValue: V, newValue: V) {
        propertyWillChangeSubject.value = Unit
    }

    override fun <V> didChange(property: KProperty<V>, oldValue: V, newValue: V) {
        propertyDidChangeSubject.value = PropertyChange(property, oldValue, newValue)
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

    private val hiddenDelegate = published(false, this)
    override var hidden: Boolean by hiddenDelegate

    fun bindHidden(publisher: Publisher<Boolean>) {
        updatePropertyPublisher(this::hidden, cancellableManager, publisher)
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::hidden.name -> hiddenDelegate as PublishedProperty<V>
            else -> null
        }

    protected fun <V> updatePropertyPublisher(
        property: KProperty<V>,
        cancellableManager: CancellableManager,
        publisher: Publisher<V>
    ) {
        publishedProperty(property)?.updatePublisher(property, publisher, cancellableManager)
    }
}
