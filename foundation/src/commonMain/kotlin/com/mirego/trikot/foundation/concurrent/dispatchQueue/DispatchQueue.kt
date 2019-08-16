package com.mirego.trikot.foundation.concurrent.dispatchQueue

import com.mirego.trikot.foundation.concurrent.freeze

typealias DispatchBlock = () -> Unit

interface DispatchQueue {
    fun dispatch(block: DispatchBlock)
}

interface QueueDispatcher {
    val dispatchQueue: DispatchQueue
}

fun QueueDispatcher.dispatch(block: DispatchBlock) {
    dispatchQueue.dispatch(freeze(block))
}
