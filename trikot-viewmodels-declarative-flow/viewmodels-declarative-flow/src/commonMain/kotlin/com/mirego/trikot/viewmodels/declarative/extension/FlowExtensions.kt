package com.mirego.trikot.viewmodels.declarative.extension

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun interface Closeable {
    fun close()
}

class VMDFlow<T : Any> internal constructor(
    private val origin: Flow<T>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : Flow<T> by origin {
    fun watch(block: (T, Closeable) -> Unit) {
        val job = Job()
        val scope = CoroutineScope(dispatcher + job)
        val closeable = Closeable { job.cancel() }

        onEach {
            block(it, closeable)
        }.launchIn(scope)
    }
}

fun <T : Any> Flow<T>.wrap(): VMDFlow<T> = VMDFlow(this)
