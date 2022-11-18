package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDPickerItemViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDPickerViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published

@Suppress("LeakingThis")
open class VMDPickerViewModelImpl<E : VMDPickerItemViewModel>(
    cancellableManager: CancellableManager,
    final override val elements: List<E>,
    initialSelectedId: String? = null
) : VMDPickerViewModel<E>(cancellableManager) {
    private val initialSelectedIndex: Int =
        elements
            .indexOfFirst { it.identifier == initialSelectedId }
            .takeIf { it >= 0 }
            ?: NO_SELECTION

    private val selectedIndexDelegate = published(initialSelectedIndex, this)

    override var selectedIndex: Int by selectedIndexDelegate

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::selectedIndex.name] = selectedIndexDelegate
        }
    }
}
