package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.components.impl.VMDControlViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import kotlinx.coroutines.CoroutineScope

abstract class VMDButtonViewModel<C : VMDContent>(coroutineScope: CoroutineScope) :
    VMDControlViewModelImpl(coroutineScope) {
    abstract val content: C
    abstract val actionBlock: () -> Unit
}
