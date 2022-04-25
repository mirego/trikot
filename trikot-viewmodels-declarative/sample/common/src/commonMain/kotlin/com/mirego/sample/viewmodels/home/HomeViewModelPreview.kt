package com.mirego.sample.viewmodels.home

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeViewModelPreview :
    VMDViewModelImpl(cancellableManager = CancellableManager()),
    HomeViewModel {
    override val title = "trikot.vmd"

    override val sections: VMDListViewModel<HomeSectionViewModel> =
        VMDComponents.List.of(
            HomeSectionViewModelImpl(
                text = "Components",
                elements = listOf(
                    HomeSectionElementViewModelImpl("Component 1", cancellableManager),
                    HomeSectionElementViewModelImpl("Component 2", cancellableManager),
                    HomeSectionElementViewModelImpl("Component 3", cancellableManager)
                ),
                cancellableManager = cancellableManager
            ),
            cancellableManager = cancellableManager
        )
}
