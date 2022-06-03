package com.mirego.kword.sample

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

class CFlow<T : Any> internal constructor(
    private val origin: Flow<T>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = Job()
        val scope = CoroutineScope(dispatcher + job)

        onEach {
            block(it)
        }.launchIn(scope)

        return Closeable { job.cancel() }
    }
}

fun <T : Any> Flow<T>.wrap(): CFlow<T> = CFlow(this)
