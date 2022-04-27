package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

abstract class VMDListViewModel<E : VMDIdentifiableContent>(cancellableManager: CancellableManager) : VMDViewModelImpl(cancellableManager) {
    abstract val elements: List<E>
}
