package com.mirego.trikot.viewmodels.declarative.viewmodel

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.ConcretePublisher
import com.mirego.trikot.streams.reactive.PublishSubjectImpl
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.asConcretePublisher
import com.mirego.trikot.viewmodels.declarative.animation.VMDAnimation
import com.mirego.trikot.viewmodels.declarative.animation.VMDAnimationContext
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPropertyChange
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

open class VMDViewModelImpl(override val cancellableManager: CancellableManager) : VMDViewModel, VMDViewModelDSL {

    private val propertyWillChangeSubject = PublishSubjectImpl<VMDPropertyChange<*>>()
    private val propertyDidChangeSubject = PublishSubjectImpl<VMDPropertyChange<*>>()

    override val propertyWillChange: ConcretePublisher<VMDPropertyChange<*>>
        get() = propertyWillChangeSubject.asConcretePublisher()

    override val propertyDidChange: ConcretePublisher<VMDPropertyChange<*>>
        get() = propertyDidChangeSubject.asConcretePublisher()

    override fun <V> willChange(property: KProperty<V>, oldValue: V, newValue: V) {
        propertyWillChangeSubject.value = VMDPropertyChange(property, oldValue, newValue, VMDAnimationContext.animationStack.peek())
    }

    override fun <V> didChange(property: KProperty<V>, oldValue: V, newValue: V) {
        propertyDidChangeSubject.value = VMDPropertyChange(property, oldValue, newValue, VMDAnimationContext.animationStack.peek())
    }

    override fun <V> publisherForProperty(property: KProperty<V>): Publisher<V> {
        return publisherForPropertyName(property.name)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publisherForPropertyName(propertyName: String): Publisher<V> {
        propertyMapping[propertyName]?.let { it as? VMDPublishedProperty<V> }
            ?.let { return it.valuePublisher }
        println("ViewModelImpl.publisherForPropertyName: propertyName $propertyName is not found or has an invalid type")
        return Publishers.never()
    }

    private val hiddenDelegate = published(false, this)
    override var isHidden: Boolean by hiddenDelegate

    fun bindHidden(publisher: Publisher<Boolean>) {
        updatePropertyPublisher(this::isHidden, cancellableManager, publisher)
    }

    fun bindHiddenAnimated(publisher: Publisher<Pair<Boolean, VMDAnimation?>>) {
        updateAnimatedPropertyPublisher(this::isHidden, cancellableManager, publisher)
    }

    protected open val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        mapOf(
            this::isHidden.name to hiddenDelegate
        )
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <V> updatePropertyPublisher(
        property: KProperty<V>,
        cancellableManager: CancellableManager,
        publisher: Publisher<V>
    ) {
        (propertyMapping[property.name] as? VMDPublishedProperty<V>)?.updatePublisher(
            property,
            publisher,
            cancellableManager
        )
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <V> updateAnimatedPropertyPublisher(
        property: KProperty<V>,
        cancellableManager: CancellableManager,
        publisher: Publisher<Pair<V, VMDAnimation?>>
    ) {
        (propertyMapping[property.name] as? VMDPublishedProperty<V>)?.updatePublisherWithAnimation(
            property,
            publisher,
            cancellableManager
        )
    }
}
