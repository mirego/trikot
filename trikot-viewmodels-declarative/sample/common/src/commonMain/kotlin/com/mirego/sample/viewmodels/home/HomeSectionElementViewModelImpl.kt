package com.mirego.sample.viewmodels.home

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeSectionElementViewModelImpl(
    text: String,
    cancellableManager: CancellableManager,
    closure: VMDButtonViewModelImpl<VMDTextContent>.() -> Unit = {}
) : VMDViewModelImpl(cancellableManager), HomeSectionElementViewModel {
    override val identifier = text
    override val button = VMDComponents.Button.withText(text, cancellableManager, closure)
}
