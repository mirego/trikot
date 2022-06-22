package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.CoroutineScope

abstract class VMDListViewModel<E : VMDIdentifiableContent>(coroutineScope: CoroutineScope) : VMDViewModelImpl(coroutineScope) {
    abstract val elements: List<E>
}
