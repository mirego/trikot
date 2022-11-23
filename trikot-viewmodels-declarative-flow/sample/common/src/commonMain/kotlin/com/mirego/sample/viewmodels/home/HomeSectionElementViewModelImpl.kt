package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.components.impl.VMDButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithText
import kotlinx.coroutines.CoroutineScope

class HomeSectionElementViewModelImpl(
    text: String,
    coroutineScope: CoroutineScope,
    closure: VMDButtonViewModelImpl<VMDTextContent>.() -> Unit = {}
) : VMDViewModelImpl(coroutineScope), HomeSectionElementViewModel {
    override val identifier = text
    override val button = buttonWithText(text, closure = closure)
}
