package com.trikot.sample.viewmodels.base

import com.mirego.trikot.foundation.ref.weakAtomicReference
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.ViewModel

abstract class BaseViewModelController<NavigationDelegate : BaseNavigationDelegate, VM : ViewModel> : ViewModelController() {
    var navigationDelegate: NavigationDelegate? by weakAtomicReference()

    protected val cancellableManager = CancellableManager()

    abstract val viewModel: VM

    override fun onCleared() {
        super.onCleared()
        cancellableManager.cancel()
    }
}
