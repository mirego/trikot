package com.mirego.trikot.viewmodels.declarative.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

@Suppress("NO_ACTUAL_CLASS_MEMBER_FOR_EXPECTED_CLASS")
actual typealias VMDPlatformViewModelController = ViewModel

actual val VMDPlatformViewModelController.platformViewModelScope: CoroutineScope
    get() = viewModelScope
