package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.viewmodels.declarative.components.VMDProgressViewModel
import com.mirego.trikot.viewmodels.declarative.properties.VMDProgressDetermination
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Suppress("LeakingThis")
open class VMDProgressViewModelImpl(coroutineScope: CoroutineScope) :
    VMDViewModelImpl(coroutineScope), VMDProgressViewModel {

    private val determinationDelegate = emit(null as VMDProgressDetermination?, this, coroutineScope)
    override var determination: VMDProgressDetermination? by determinationDelegate

    fun bindDetermination(flow: Flow<VMDProgressDetermination?>) {
        updateProperty(this::determination, flow)
    }

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::determination.name] = determinationDelegate
        }
    }
}
