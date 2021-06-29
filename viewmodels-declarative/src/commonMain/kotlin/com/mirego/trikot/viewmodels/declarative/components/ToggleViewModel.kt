package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.impl.ControlViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.Content

abstract class ToggleViewModel<C : Content>(cancellableManager: CancellableManager) :
    ControlViewModelImpl(cancellableManager) {
    abstract val isOn: Boolean
    abstract val content: C

    open fun onValueChange(isOn: Boolean) {}
}
