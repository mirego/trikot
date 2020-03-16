package com.mirego.trikot.foundation.concurrent.dispatchQueue

actual open class OperationDispatchQueue : DispatchQueue {
    override fun isSerial() = true
    override fun dispatch(block: DispatchBlock) = block()
}

actual class SerialSubscriptionDispatchQueue : OperationDispatchQueue(), DispatchQueue
