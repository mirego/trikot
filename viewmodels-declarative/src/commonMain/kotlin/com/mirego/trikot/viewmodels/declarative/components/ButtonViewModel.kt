package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.impl.ControlViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.Content

abstract class ButtonViewModel<C : Content>(cancellableManager: CancellableManager) :
    ControlViewModelImpl(cancellableManager) {
    abstract val content: C
    abstract val action: () -> Unit
}
