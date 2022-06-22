package com.mirego.trikot.viewmodels.declarative.viewmodel

import com.mirego.trikot.viewmodels.declarative.util.CoroutineScopeProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

abstract class VMDLifecycleViewModelImpl(coroutineScope: CoroutineScope) : VMDLifecycleViewModel, VMDViewModelImpl(coroutineScope) {

    private var firstOnAppear = true
    var viewLifecycleCoroutineScope: CoroutineScope? = null
        private set

    final override fun onAppear() {
        viewLifecycleCoroutineScope?.cancel()
        viewLifecycleCoroutineScope = CoroutineScopeProvider.provideMainWithSuperviserJob(
            CoroutineExceptionHandler { _, exception ->
                println("CoroutineExceptionHandler got $exception")
            }
        ).also {
            onAppear(it)
            if (firstOnAppear) {
                firstOnAppear = false
                onAppearFirst(coroutineScope)
            } else {
                onAppearSubsequent(it)
            }
        }
    }

    protected open fun onAppear(coroutineScope: CoroutineScope) {
    }

    protected open fun onAppearFirst(coroutineScope: CoroutineScope) {
    }

    protected open fun onAppearSubsequent(coroutineScope: CoroutineScope) {
    }

    override fun onDisappear() {
        viewLifecycleCoroutineScope?.cancel()
        viewLifecycleCoroutineScope = null
    }
}
