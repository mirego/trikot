package com.mirego.trikot.viewmodels.declarative.extension

import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextFlowImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairFlowContent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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

fun Flow<String>.asVMDTextContent(): Flow<VMDTextContent> = flow {
    this@asVMDTextContent.collect { emit(VMDTextContent(it)) }
}

fun Flow<VMDTextPairFlowContent>.asVMDTextPairContent(): Flow<VMDTextPairContent> = flow {
    this@asVMDTextPairContent.collect { content ->
        content.first.combine(content.second) { text1, text2 ->
            VMDTextPairContent(text1, text2)
        }.collect { emit(it) }
    }
}

fun Flow<VMDTextFlowImagePairContent>.asVMDTextImagePairContent(): Flow<VMDTextImagePairContent> = flow {
    this@asVMDTextImagePairContent.collect { content ->
        flowOf(content.image).combine(content.text) { image, text ->
            VMDTextImagePairContent(text, image)
        }.collect { emit(it) }
    }
}
