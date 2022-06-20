package com.mirego.trikot.viewmodels.declarative.controller

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.foundation.ref.weakAtomicReference
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class VMDViewModelController<VM : VMDViewModel, N : VMDNavigationDelegate> :
    VMDPlatformViewModelController() {

    var navigationDelegate: N? by weakAtomicReference()

    protected val viewModelControllerScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    abstract val viewModel: VM

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

    override fun onCleared() {
        super.onCleared()
        viewModelControllerScope.cancel()
    }
}
