package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

abstract class VMDPickerViewModel<E : VMDPickerItemViewModel>(cancellableManager: CancellableManager) : VMDViewModel, VMDListViewModel<E>(cancellableManager) {
    companion object {
        const val NO_SELECTION = -1
    }

    abstract var selectedIndex: Int
}
