package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.MainScope

class HomeViewModelPreview :
    VMDViewModelImpl(coroutineScope = MainScope()),
    HomeViewModel {
    override val title = "trikot.vmd"

    override val sections: VMDListViewModel<HomeSectionViewModel> =
        VMDComponents.List.of(
            HomeSectionViewModelImpl(
                text = "Components",
                elements = listOf(
                    HomeSectionElementViewModelImpl("Component 1", coroutineScope),
                    HomeSectionElementViewModelImpl("Component 2", coroutineScope),
                    HomeSectionElementViewModelImpl("Component 3", coroutineScope)
                ),
                coroutineScope = coroutineScope
            ),
            coroutineScope = coroutineScope
        )
}
