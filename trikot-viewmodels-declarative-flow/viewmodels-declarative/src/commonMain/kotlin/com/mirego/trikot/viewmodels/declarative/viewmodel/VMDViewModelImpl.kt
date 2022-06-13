package com.mirego.trikot.viewmodels.declarative.viewmodel

import com.mirego.trikot.viewmodels.declarative.animation.VMDAnimation
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPropertyChange
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

open class VMDViewModelImpl(protected val coroutineScope: CoroutineScope) : VMDViewModel {

    private val propertyWillChangeSubject = MutableSharedFlow<VMDPropertyChange<*>>()
    private val propertyDidChangeSubject = MutableSharedFlow<VMDPropertyChange<*>>()

    override val propertyWillChange: Flow<VMDPropertyChange<*>>
        get() = propertyWillChangeSubject

    override val propertyDidChange: Flow<VMDPropertyChange<*>>
        get() = propertyDidChangeSubject

    override fun <V> willChange(property: KProperty<V>, oldValue: V, newValue: V) {
        coroutineScope.launch {
            propertyWillChangeSubject.emit(VMDPropertyChange(property, oldValue, newValue))
        }
    }

    override fun <V> didChange(property: KProperty<V>, oldValue: V, newValue: V) {
        coroutineScope.launch {
            propertyDidChangeSubject.emit(VMDPropertyChange(property, oldValue, newValue))
        }
    }

    override fun <V> flowForProperty(property: KProperty<V>): Flow<V> {
        return flowForPropertyName(property.name)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> flowForPropertyName(propertyName: String): Flow<V> {
        propertyMapping[propertyName]?.let { it as? VMDFlowProperty<V> }?.let { return it.internalFlow }
        println("ViewModelImpl.publisherForPropertyName: propertyName $propertyName is not found or has an invalid type")
        return emptyFlow()
    }

    private val hiddenDelegate = emit(false, this, coroutineScope)
    override var isHidden: Boolean by hiddenDelegate

    fun bindHidden(flow: Flow<Boolean>) {
        updateProperty(this::isHidden, flow)
    }

    protected open val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        mapOf(
            this::isHidden.name to hiddenDelegate
        )
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <V> updateProperty(
        property: KProperty<V>,
        flow: Flow<V>
    ) {
        (propertyMapping[property.name] as? VMDFlowProperty<V>)?.updateFlow(
            property,
            flow
        )
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <V> updateAnimatedPropertyPublisher(
        property: KProperty<V>,
        flow: Flow<Pair<V, VMDAnimation?>>
    ) {
        (propertyMapping[property.name] as? VMDFlowProperty<V>)?.updateFlowWithAnimation(
            property,
            flow
        )
    }
}
