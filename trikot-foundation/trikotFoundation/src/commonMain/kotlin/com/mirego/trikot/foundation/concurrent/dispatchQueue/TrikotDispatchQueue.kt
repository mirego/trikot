package com.mirego.trikot.foundation.concurrent.dispatchQueue

typealias DispatchBlock = () -> Unit

interface TrikotDispatchQueue {
    fun isSerial() = false
    fun dispatch(block: DispatchBlock)
}

interface TrikotQueueDispatcher {
    val dispatchQueue: TrikotDispatchQueue
}

fun TrikotQueueDispatcher.dispatch(block: DispatchBlock) {
    dispatchQueue.dispatch(block)
}
