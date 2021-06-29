package com.mirego.sample.viewmodels.home.listItem

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.impl.TextViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModelImpl

class HomeListItemViewModelImpl(
    identifier: String,
    cancellableManager: CancellableManager
) :
    ViewModelImpl(cancellableManager), HomeListItemViewModel {
    override var id = identifier
    override var name = TextViewModelImpl(cancellableManager)
}
