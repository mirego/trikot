package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Suppress("LeakingThis")
open class VMDButtonViewModelImpl<C : VMDContent>(
    coroutineScope: CoroutineScope,
    defaultContent: C
) : VMDButtonViewModel<C>(coroutineScope) {

    override var actionBlock: () -> Unit by atomic {}

    private val contentDelegate = emit(defaultContent, this, coroutineScope)
    override var content: C by contentDelegate

    fun setAction(action: () -> Unit) {
        actionBlock = {
            action.invoke()
        }
    }

    fun <T> setAction(flow: Flow<T>, action: (T) -> Unit) {
        actionBlock = {
            coroutineScope.launch {
                val value = flow.firstOrNull()
                value?.let { action(it) }
            }
        }
    }

    fun bindContent(flow: Flow<C>) {
        updateProperty(this::content, flow)
    }

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::content.name] = contentDelegate
        }
    }
}
