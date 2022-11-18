package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.viewmodels.declarative.components.VMDPickerItemViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDPickerViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope

@Suppress("LeakingThis")
open class VMDPickerViewModelImpl<E : VMDPickerItemViewModel>(
    coroutineScope: CoroutineScope,
    final override val elements: List<E>,
    initialSelectedId: String? = null
) : VMDPickerViewModel<E>(coroutineScope) {
    private val initialSelectedIndex: Int =
        elements
            .indexOfFirst { it.identifier == initialSelectedId }
            .takeIf { it >= 0 }
            ?: NO_SELECTION

    private val selectedIndexDelegate = emit(initialSelectedIndex, this, coroutineScope)

    override var selectedIndex: Int by selectedIndexDelegate

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::selectedIndex.name] = selectedIndexDelegate
        }
    }
}
