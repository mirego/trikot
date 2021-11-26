package com.mirego.sample.viewmodels.home.listItem

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDTextViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeListItemViewModelImpl(
    identifier: String,
    cancellableManager: CancellableManager
) :
    VMDViewModelImpl(cancellableManager), HomeListItemViewModel {
    override var id = identifier
    override var name = VMDTextViewModelImpl(cancellableManager)
}
