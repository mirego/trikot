package com.mirego.sample.viewmodels.home

import com.mirego.sample.viewmodels.home.listItem.HomeListItemViewModel
import com.mirego.sample.viewmodels.home.listItem.HomeListItemViewModelImpl
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDTextViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeViewModelImpl(cancellableManager: CancellableManager) :
    VMDViewModelImpl(cancellableManager), HomeViewModel {

    override val title = VMDTextViewModelImpl(cancellableManager).apply {
        text = "Components"
    }

    override val items: List<HomeListItemViewModel> = listOf(
        HomeListItemViewModelImpl("item-text", cancellableManager).apply {
            name.text = "Text"
        },
        HomeListItemViewModelImpl("item-button", cancellableManager).apply {
            name.text = "Button"
        },
        HomeListItemViewModelImpl("item-image", cancellableManager).apply {
            name.text = "Image"
        },
        HomeListItemViewModelImpl("item-textfield", cancellableManager).apply {
            name.text = "TextField"
        },
        HomeListItemViewModelImpl("item-toggle", cancellableManager).apply {
            name.text = "Toggle"
        }
    )
}
