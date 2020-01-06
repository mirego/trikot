package com.mirego.trikot.foundation.concurrent.dispatchQueue

typealias DispatchBlock = () -> Unit

interface DispatchQueue {
    fun isSerial() = false
    fun dispatch(block: DispatchBlock)
}

interface QueueDispatcher {
    val dispatchQueue: DispatchQueue
}

fun QueueDispatcher.dispatch(block: DispatchBlock) {
    dispatchQueue.dispatch(block)
}
