package com.mirego.sample.viewmodels.home

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeSectionViewModelImpl(
    text: String,
    override val elements: List<HomeSectionElementViewModel>,
    cancellableManager: CancellableManager
) : VMDViewModelImpl(cancellableManager), HomeSectionViewModel {
    override val identifier = text
    override val title = VMDComponents.Text.withContent(text, cancellableManager)
}
