package com.mirego.sample.viewmodels.home

import com.mirego.sample.viewmodels.home.listItem.HomeListItemViewModelPreview
import com.mirego.sample.viewmodels.preview.TextViewModelPreview
import com.mirego.sample.viewmodels.preview.ViewModelPreview

class HomeViewModelPreview : ViewModelPreview(), HomeViewModel {
    override val title = TextViewModelPreview(text = "Components")
    override val items = listOf(
        HomeListItemViewModelPreview("Component 1"),
        HomeListItemViewModelPreview("Component 2"),
        HomeListItemViewModelPreview("Component 3")
    )
}
