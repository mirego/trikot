package com.mirego.trikot.viewmodels.declarative.controller

import kotlinx.coroutines.CoroutineScope

expect abstract class VMDPlatformViewModelController() {
    open fun onCleared()
}

expect val VMDPlatformViewModelController.platformViewModelScope: CoroutineScope
