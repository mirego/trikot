package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.CoroutineScope

class HomeSectionElementViewModelImpl(
    text: String,
    coroutineScope: CoroutineScope,
    closure: VMDButtonViewModelImpl<VMDTextContent>.() -> Unit = {}
) : VMDViewModelImpl(coroutineScope), HomeSectionElementViewModel {
    override val identifier = text
    override val button = VMDComponents.Button.withText(text, coroutineScope, closure)
}
