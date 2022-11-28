package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.viewmodels.declarative.animation.VMDAnimation
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Suppress("LeakingThis")
open class VMDListViewModelImpl<E : VMDIdentifiableContent>(coroutineScope: CoroutineScope) :
    VMDListViewModel<E>(coroutineScope) {

    private val elementsDelegate: VMDFlowProperty<List<E>> = emit(emptyList(), this, coroutineScope)
    override var elements: List<E> by elementsDelegate

    fun bindElements(flow: Flow<List<E>>) {
        updateProperty(this::elements, flow)
    }

    fun bindElementsAnimated(flow: Flow<Pair<List<E>, VMDAnimation?>>) {
        updateAnimatedPropertyPublisher(this::elements, flow)
    }

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::elements.name] = elementsDelegate
        }
    }
}
