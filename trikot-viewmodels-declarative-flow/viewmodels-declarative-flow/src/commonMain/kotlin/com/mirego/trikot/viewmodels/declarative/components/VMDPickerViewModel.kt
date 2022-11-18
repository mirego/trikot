package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
import kotlinx.coroutines.CoroutineScope

abstract class VMDPickerViewModel<E : VMDPickerItemViewModel>(coroutineScope: CoroutineScope) : VMDViewModel, VMDListViewModel<E>(coroutineScope) {
    companion object {
        const val NO_SELECTION = -1
    }

    abstract var selectedIndex: Int
}
