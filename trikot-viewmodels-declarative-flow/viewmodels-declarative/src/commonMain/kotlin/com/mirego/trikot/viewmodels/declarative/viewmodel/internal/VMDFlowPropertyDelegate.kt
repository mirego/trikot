package com.mirego.trikot.viewmodels.declarative.viewmodel.internal

import kotlinx.coroutines.CoroutineScope

fun <T> emit(initialValue: T, listener: VMDPropertyChangeListener, coroutineScope: CoroutineScope) =
    object : VMDFlowProperty<T>(initialValue, listener, coroutineScope) {}
