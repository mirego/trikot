package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDControlViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDContent

abstract class VMDButtonViewModel<C : VMDContent>(cancellableManager: CancellableManager) :
    VMDControlViewModelImpl(cancellableManager) {
    abstract val content: C
    abstract val action: () -> Unit
}
