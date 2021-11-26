package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDControlViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDContent

abstract class VMDToggleViewModel<C : VMDContent>(cancellableManager: CancellableManager) :
    VMDControlViewModelImpl(cancellableManager) {
    abstract val isOn: Boolean
    abstract val content: C

    open fun onValueChange(isOn: Boolean) {}
}
