package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.CoroutineScope

class HomeSectionViewModelImpl(
    text: String,
    override val elements: List<HomeSectionElementViewModel>,
    coroutineScope: CoroutineScope
) : VMDViewModelImpl(coroutineScope), HomeSectionViewModel {
    override val identifier = text
    override val title = VMDComponents.Text.withContent(text, coroutineScope)
}
