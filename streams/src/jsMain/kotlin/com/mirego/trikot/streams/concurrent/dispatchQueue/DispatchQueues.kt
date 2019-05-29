package com.mirego.trikot.streams.concurrent.dispatchQueue

actual open class OperationDispatchQueue : DispatchQueue {
    override fun dispatch(block: DispatchBlock) {
        block()
    }
}

actual class SerialSubscriptionDispatchQueue : OperationDispatchQueue(), DispatchQueue
