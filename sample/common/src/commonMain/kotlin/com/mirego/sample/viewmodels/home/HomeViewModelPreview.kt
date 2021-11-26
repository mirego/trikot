package com.mirego.sample.viewmodels.home

import com.mirego.sample.viewmodels.home.listItem.HomeListItemViewModelPreview
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponentsFactory
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeViewModelPreview :
    VMDViewModelImpl(cancellableManager = CancellableManager()),
    HomeViewModel {

    override val title =
        VMDComponentsFactory.Companion.Text.withContent("Components", cancellableManager)

    override val items = listOf(
        HomeListItemViewModelPreview("Component 1"),
        HomeListItemViewModelPreview("Component 2"),
        HomeListItemViewModelPreview("Component 3")
    )
}
