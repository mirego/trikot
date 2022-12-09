package com.mirego.trikot.viewmodels.declarative.controller

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.foundation.ref.weakAtomicReference
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
import kotlinx.coroutines.CoroutineScope

abstract class VMDViewModelController<VM : VMDViewModel, N : VMDNavigationDelegate> :
    VMDPlatformViewModelController() {

    var navigationDelegate: N? by weakAtomicReference()

    abstract val viewModel: VM

    val viewModelScope: CoroutineScope = platformViewModelScope

    private var hasAppeared by atomic(false)

    open fun onCreate() {
    }

    open fun onAppear() {
        if (!hasAppeared) {
            onAppearFirst()
        } else {
            onAppearSubsequent()
        }
        hasAppeared = true
    }

    protected open fun onAppearSubsequent() {
    }

    protected open fun onAppearFirst() {
    }

    open fun onDisappear() {
    }
}
