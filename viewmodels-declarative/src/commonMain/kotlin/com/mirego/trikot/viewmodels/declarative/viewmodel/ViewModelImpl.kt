package com.mirego.trikot.viewmodels.declarative.viewmodel

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.ConcretePublisher
import com.mirego.trikot.streams.reactive.PublishSubjectImpl
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.asConcretePublisher
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PropertyChange
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
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

    override fun <V> publisherForProperty(property: KProperty<V>): Publisher<V> {
        return publisherForPropertyName(property.name)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publisherForPropertyName(propertyName: String): Publisher<V> {
        propertyMapping[propertyName]?.let { it as? PublishedProperty<V> }
            ?.let { return it.valuePublisher }
        println("ViewModelImpl.publisherForPropertyName: propertyName $propertyName is not found or has an invalid type")
        return Publishers.never()
    }

    private val hiddenDelegate = published(false, this)
    override var hidden: Boolean by hiddenDelegate

    fun bindHidden(publisher: Publisher<Boolean>) {
        updatePropertyPublisher(this::hidden, cancellableManager, publisher)
    }

    protected open val propertyMapping: Map<String, PublishedProperty<*>> by lazy {
        mapOf(
            this::hidden.name to hiddenDelegate
        )
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <V> updatePropertyPublisher(
        property: KProperty<V>,
        cancellableManager: CancellableManager,
        publisher: Publisher<V>
    ) {
        (propertyMapping[property.name] as? PublishedProperty<V>)?.updatePublisher(
            property,
            publisher,
            cancellableManager
        )
    }
}
