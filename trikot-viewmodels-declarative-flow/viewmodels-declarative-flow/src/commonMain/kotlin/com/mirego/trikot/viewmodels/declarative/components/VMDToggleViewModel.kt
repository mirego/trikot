package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.components.impl.VMDControlViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import kotlinx.coroutines.CoroutineScope

abstract class VMDToggleViewModel<L : VMDContent>(coroutineScope: CoroutineScope) :
    VMDControlViewModelImpl(coroutineScope) {
    abstract val isOn: Boolean
    abstract val label: L

    open fun onValueChange(isOn: Boolean) {}
}
