package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.impl.ViewModelImpl
import com.mirego.trikot.viewmodels.declarative.properties.IdentifiableContent

abstract class ListViewModel<C : IdentifiableContent>(cancellableManager: CancellableManager) : ViewModelImpl(cancellableManager) {
    abstract val elements: List<C>
}
