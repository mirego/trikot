package com.mirego.trikot.viewmodels.declarative.controller

import androidx.lifecycle.ViewModel

actual abstract class VMDPlatformViewModelController actual constructor() : ViewModel() {
    actual override fun onCleared() {
        super.onCleared()
    }
}
