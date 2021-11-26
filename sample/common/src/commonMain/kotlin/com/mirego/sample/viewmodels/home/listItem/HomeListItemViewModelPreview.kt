package com.mirego.sample.viewmodels.home.listItem

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponentsFactory
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeListItemViewModelPreview(
    componentName: String
) : VMDViewModelImpl(CancellableManager()), HomeListItemViewModel {
    override val id: String = componentName
    override val name: VMDTextViewModel =
        VMDComponentsFactory.Companion.Text.withContent(componentName, cancellableManager)
}
