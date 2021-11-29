package com.mirego.sample.viewmodels.home

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponentsFactory
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeViewModelPreview :
    VMDViewModelImpl(cancellableManager = CancellableManager()),
    HomeViewModel {

    override val title =
        VMDComponentsFactory.Companion.Text.withContent("Components", cancellableManager)

    override val items = listOf(
        VMDComponentsFactory.Companion.Button.withText("Component 1", cancellableManager),
        VMDComponentsFactory.Companion.Button.withText("Component 2", cancellableManager),
        VMDComponentsFactory.Companion.Button.withText("Component 3", cancellableManager)
    )
}
