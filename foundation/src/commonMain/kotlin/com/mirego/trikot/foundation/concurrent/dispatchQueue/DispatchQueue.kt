package com.mirego.trikot.foundation.concurrent.dispatchQueue

import com.mirego.trikot.foundation.concurrent.freeze

typealias DispatchBlock = () -> Unit

interface DispatchQueue {
    fun isSerial() = false
    fun dispatch(block: DispatchBlock)
}

interface QueueDispatcher {
    val dispatchQueue: DispatchQueue
}

fun QueueDispatcher.dispatch(block: DispatchBlock) {
    freeze(block)
    dispatchQueue.dispatch(block)
}
