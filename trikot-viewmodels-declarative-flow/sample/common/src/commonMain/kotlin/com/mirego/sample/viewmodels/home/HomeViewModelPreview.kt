package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.list
import kotlinx.coroutines.MainScope

class HomeViewModelPreview :
    VMDViewModelImpl(coroutineScope = MainScope()),
    HomeViewModel {
    override val title = "trikot.vmd"

    override val sections: VMDListViewModel<HomeSectionViewModel> =
        list(
            HomeSectionViewModelImpl(
                text = "Components",
                elements = listOf(
                    HomeSectionElementViewModelImpl("Component 1", coroutineScope),
                    HomeSectionElementViewModelImpl("Component 2", coroutineScope),
                    HomeSectionElementViewModelImpl("Component 3", coroutineScope)
                ),
                coroutineScope = coroutineScope
            )
        )
}
