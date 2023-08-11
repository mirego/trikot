package com.mirego.trikot.viewmodels.declarative.controller

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

actual abstract class VMDPlatformViewModelController {
    private val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("CoroutineExceptionHandler got $throwable")
    }

    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + exceptionHandler)

    actual open fun onCleared() {
        coroutineScope.cancel()
    }
}

actual val VMDPlatformViewModelController.platformViewModelScope: CoroutineScope
    get() = coroutineScope
