package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Suppress("LeakingThis")
open class VMDTextViewModelImpl(coroutineScope: CoroutineScope) :
    VMDViewModelImpl(coroutineScope), VMDTextViewModel {

    private val textDelegate = emit("", this, coroutineScope)
    override var text: String by textDelegate

    fun bindText(flow: Flow<String>) {
        updateProperty(this::text, flow)
    }

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::text.name] = textDelegate
        }
    }
}
