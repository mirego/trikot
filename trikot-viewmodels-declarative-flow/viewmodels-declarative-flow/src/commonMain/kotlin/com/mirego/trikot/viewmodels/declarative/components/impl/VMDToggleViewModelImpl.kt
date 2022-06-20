package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.viewmodels.declarative.components.VMDToggleViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Suppress("LeakingThis")
open class VMDToggleViewModelImpl<C : VMDContent>(
    coroutineScope: CoroutineScope,
    defaultContent: C
) : VMDToggleViewModel<C>(coroutineScope) {

    private val isOnDelegate = emit(false, this, coroutineScope)
    override var isOn: Boolean by isOnDelegate

    private val contentDelegate = emit(defaultContent, this, coroutineScope)
    override var label: C by contentDelegate

    override fun onValueChange(isOn: Boolean) {
        this.isOn = isOn
    }

    fun bindContent(flow: Flow<C>) {
        updateProperty(this::label, flow)
    }

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::isOn.name] = isOnDelegate
            it[this::label.name] = contentDelegate
        }
    }
}
