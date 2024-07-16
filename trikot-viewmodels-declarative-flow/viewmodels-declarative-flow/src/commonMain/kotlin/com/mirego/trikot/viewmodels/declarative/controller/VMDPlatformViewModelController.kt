package com.mirego.trikot.viewmodels.declarative.controller

import kotlinx.coroutines.CoroutineScope

expect abstract class VMDPlatformViewModelController() {
    protected open fun onCleared()
}

expect val VMDPlatformViewModelController.platformViewModelScope: CoroutineScope
