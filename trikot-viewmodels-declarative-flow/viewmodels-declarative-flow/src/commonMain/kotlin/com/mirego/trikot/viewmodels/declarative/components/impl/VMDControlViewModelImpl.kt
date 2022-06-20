package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.viewmodels.declarative.components.VMDControlViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Suppress("LeakingThis")
open class VMDControlViewModelImpl(coroutineScope: CoroutineScope) : VMDControlViewModel,
    VMDViewModelImpl(coroutineScope) {

    private val enabledDelegate = emit(true, this, coroutineScope)
    override var isEnabled: Boolean by enabledDelegate

    fun bindEnabled(flow: Flow<Boolean>) {
        updateProperty(this::isEnabled, flow)
    }

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::isEnabled.name] = enabledDelegate
        }
    }
}
