package com.mirego.trikot.viewmodels.declarative.viewmodel.internal

import com.mirego.trikot.foundation.ref.weakAtomicReference
import com.mirego.trikot.viewmodels.declarative.animation.VMDAnimation
import com.mirego.trikot.viewmodels.declarative.animation.withAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

abstract class VMDFlowProperty<V>(
    initialValue: V,
    listener: VMDPropertyChangeListener,
    private val coroutineScope: CoroutineScope
) :
    ObservableProperty<V>(initialValue) {
    private data class ValueContainer<T>(val value: T)

    private var listener: VMDPropertyChangeListener? by weakAtomicReference()

    private val internalValueStateFlow = MutableStateFlow(ValueContainer(initialValue))
    val internalFlow = internalValueStateFlow.map { it.value }

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
        return internalValueStateFlow.value.value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        val oldValue = internalValueStateFlow.value.value
        if (!beforeChange(property, oldValue, value)) {
            return
        }
        this.internalValueStateFlow.value = ValueContainer(value)
        afterChange(property, oldValue, value)
    }

    fun updateFlow(
        property: KProperty<V>,
        flow: Flow<V>
    ) {
        coroutineScope.launch {
            flow
                .catch { println("Error setting flow property \"${property.name}\" on ${this@VMDFlowProperty.listener}\n$it") }
                .collect { setValue(this, property, it) }
        }
    }

    fun updateFlowWithAnimation(
        property: KProperty<V>,
        flow: Flow<Pair<V, VMDAnimation?>>
    ) {
        coroutineScope.launch {
            withContext(Dispatchers.Main.immediate) {
                flow
                    .catch { println("Error setting published property \"${property.name}\" on ${this@VMDFlowProperty.listener}\n$it") }
                    .collect {
                        val value = it.first
                        val animation = it.second
                        if (animation != null) {
                            withAnimation(animation) {
                                setValue(this, property, value)
                            }
                        } else {
                            setValue(this, property, value)
                        }
                    }
            }
        }
    }
}
