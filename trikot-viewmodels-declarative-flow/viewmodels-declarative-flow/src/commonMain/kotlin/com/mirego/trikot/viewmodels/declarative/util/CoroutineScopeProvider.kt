package com.mirego.trikot.viewmodels.declarative.util

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

// This class is necessary for Swift
object CoroutineScopeProvider {
    fun provideMainWithSuperviserJob() = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    fun provideMainWithSuperviserJob(exceptionHandler: CoroutineExceptionHandler) = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + exceptionHandler)
}
