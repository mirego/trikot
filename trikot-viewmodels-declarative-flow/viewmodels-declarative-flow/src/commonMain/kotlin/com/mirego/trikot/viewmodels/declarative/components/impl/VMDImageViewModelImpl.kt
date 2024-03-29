package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.viewmodels.declarative.components.VMDImageViewModel
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Suppress("LeakingThis")
open class VMDImageViewModelImpl(coroutineScope: CoroutineScope) :
    VMDViewModelImpl(coroutineScope), VMDImageViewModel {

    private val imageDelegate =
        emit(VMDImageDescriptor.Local(VMDImageResource.None) as VMDImageDescriptor, this, coroutineScope)
    override var image: VMDImageDescriptor by imageDelegate

    private val contentDescriptionDelegate: VMDFlowProperty<String?> =
        emit(null, this, coroutineScope)
    override var contentDescription: String? by contentDescriptionDelegate

    fun bindImage(flow: Flow<VMDImageDescriptor>) {
        updateProperty(this::image, flow)
    }

    fun bindContentDescription(flow: Flow<String?>) {
        updateProperty(this::contentDescription, flow)
    }

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::image.name] = imageDelegate
            it[this::contentDescription.name] = contentDescriptionDelegate
        }
    }
}
