package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.viewmodels.declarative.components.VMDLoadingViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Suppress("LeakingThis")
open class VMDLoadingViewModelImpl(coroutineScope: CoroutineScope) :
    VMDViewModelImpl(coroutineScope), VMDLoadingViewModel {

    private val isLoadingDelegate = emit(false, this, coroutineScope)
    override var isLoading: Boolean by isLoadingDelegate

    fun bindIsLoading(flow: Flow<Boolean>) {
        updateProperty(
            this::isLoading,
            flow
        )
    }

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::isLoading.name] = isLoadingDelegate
        }
    }
}
