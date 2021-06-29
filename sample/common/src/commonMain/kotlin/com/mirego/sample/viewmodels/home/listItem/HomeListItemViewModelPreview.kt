package com.mirego.sample.viewmodels.home.listItem

import com.mirego.sample.viewmodels.preview.TextViewModelPreview
import com.mirego.sample.viewmodels.preview.ViewModelPreview
import com.mirego.trikot.viewmodels.declarative.components.TextViewModel

class HomeListItemViewModelPreview(
    componentName: String
) : ViewModelPreview(), HomeListItemViewModel {
    override val id: String = componentName
    override val name: TextViewModel = TextViewModelPreview(text = componentName)
}
