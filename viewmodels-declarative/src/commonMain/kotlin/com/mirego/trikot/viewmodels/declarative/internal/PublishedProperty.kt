package com.mirego.trikot.viewmodels.declarative.internal

import com.mirego.trikot.foundation.ref.weakAtomicReference
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.observeOn
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.viewmodels.declarative.utilities.DispatchQueues
import com.mirego.trikot.viewmodels.declarative.utilities.valueOrThrow
import org.reactivestreams.Publisher
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

abstract class PublishedProperty<V>(initialValue: V, listener: PropertyChangeListener) : ObservableProperty<V>(initialValue) {
    private data class ValueContainer<T>(val value: T)

    private var listener: PropertyChangeListener? by weakAtomicReference()

    private val internalValuePublisher = BehaviorSubjectImpl(ValueContainer(initialValue))
    val valuePublisher = internalValuePublisher.map { it.value }

    private val cancellableManagerProvider = CancellableManagerProvider()

    init {
        this.listener = listener
    }

    override fun beforeChange(property: KProperty<*>, oldValue: V, newValue: V): Boolean {
        listener?.willChange(property, oldValue, newValue)
        return true
    }

    override fun afterChange(property: KProperty<*>, oldValue: V, newValue: V) {
        listener?.didChange(property, oldValue, newValue)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): V {
        return internalValuePublisher.valueOrThrow.value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        val oldValue = internalValuePublisher.valueOrThrow.value
        if (!beforeChange(property, oldValue, value)) {
            return
        }
        this.internalValuePublisher.value = ValueContainer(value)
        afterChange(property, oldValue, value)
    }

    fun updatePublisher(property: KProperty<V>, publisher: Publisher<V>, cancellableManager: CancellableManager) {
        val propertyCancellableManager = cancellableManagerProvider.cancelPreviousAndCreate()
        cancellableManager.add(propertyCancellableManager)

        publisher
            .observeOn(DispatchQueues.uiQueue)
            .subscribe(
                propertyCancellableManager,
                onNext = {
                    setValue(this, property, it)
                },
                onError = {
                    println("Error setting published property \"${property.name}\" on ${this.listener}\n$it")
                }
            )
    }
}
