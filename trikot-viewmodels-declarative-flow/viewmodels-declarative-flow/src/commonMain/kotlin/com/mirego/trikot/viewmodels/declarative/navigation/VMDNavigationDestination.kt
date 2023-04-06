package com.mirego.trikot.viewmodels.declarative.navigation

abstract class VMDNavigationDestination<T : VMDNavigationResult> {
    abstract val type: VMDNavigationDestinationType
    abstract val input: VMDNavigationInput?
    abstract val resultCallback: ((T) -> Unit)?

    val identifier: String
        get() = this::class.simpleName.orEmpty()

    internal val internalResultCallback: (VMDNavigationResult) -> Unit = {
        resultCallback?.invoke(it as T)
    }
}
