package com.mirego.trikot.viewmodels.declarative.viewmodel

import com.mirego.trikot.viewmodels.declarative.animation.VMDAnimation
import com.mirego.trikot.viewmodels.declarative.animation.VMDAnimationContext
import com.mirego.trikot.viewmodels.declarative.extension.VMDFlow
import com.mirego.trikot.viewmodels.declarative.extension.wrap
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPropertyChange
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.reflect.KProperty

open class VMDViewModelImpl(override val coroutineScope: CoroutineScope) : VMDViewModel, VMDViewModelDSL {

    private val propertyWillChangeSubject = MutableSharedFlow<VMDPropertyChange<*>>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private val propertyDidChangeSubject = MutableSharedFlow<VMDPropertyChange<*>>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val propertyWillChange: VMDFlow<VMDPropertyChange<*>>
        get() = propertyWillChangeSubject.wrap()

    override val propertyDidChange: VMDFlow<VMDPropertyChange<*>>
        get() = propertyDidChangeSubject.wrap()

    override fun <V> willChange(property: KProperty<V>, oldValue: V, newValue: V) {
        propertyWillChangeSubject.tryEmit(VMDPropertyChange(property, oldValue, newValue, VMDAnimationContext.animationStack.peek()))
    }

    override fun <V> didChange(property: KProperty<V>, oldValue: V, newValue: V) {
        propertyDidChangeSubject.tryEmit(VMDPropertyChange(property, oldValue, newValue, VMDAnimationContext.animationStack.peek()))
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

    private val testIdentifierDelegate: VMDFlowProperty<String?> = emit(null, this, coroutineScope)
    override var testIdentifier: String? by testIdentifierDelegate

    fun bindHidden(flow: Flow<Boolean>) {
        updateProperty(this::isHidden, flow)
    }

    fun bindTestIdentifier(flow: Flow<String?>) {
        updateProperty(this::testIdentifier, flow)
    }

    protected open val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        mapOf(
            this::isHidden.name to hiddenDelegate,
            this::testIdentifier.name to testIdentifierDelegate
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
