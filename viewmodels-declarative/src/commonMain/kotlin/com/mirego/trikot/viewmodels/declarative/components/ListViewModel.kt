package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.content.IdentifiableContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModelImpl

abstract class ListViewModel<C : IdentifiableContent>(cancellableManager: CancellableManager) :
    ViewModelImpl(cancellableManager) {
    abstract val elements: List<C>
}
