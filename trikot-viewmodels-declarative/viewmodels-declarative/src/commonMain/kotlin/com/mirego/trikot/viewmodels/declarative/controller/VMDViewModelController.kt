package com.mirego.trikot.viewmodels.declarative.controller

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.foundation.ref.weakAtomicReference
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

abstract class VMDViewModelController<VM : VMDViewModel, N : VMDNavigationDelegate> :
    VMDPlatformViewModelController() {

    var navigationDelegate: N? by weakAtomicReference()

    protected val cancellableManager = CancellableManager()

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
        cancellableManager.cancel()
    }
}
