package com.mirego.trikot.viewmodels.declarative.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

// This class is necessary for Swift
class CoroutineScopeProvider {
    fun provideMainWithSuperviserJob() = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
}
