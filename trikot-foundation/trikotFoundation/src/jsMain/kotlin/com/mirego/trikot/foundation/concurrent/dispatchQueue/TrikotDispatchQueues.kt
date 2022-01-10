package com.mirego.trikot.foundation.concurrent.dispatchQueue

actual open class OperationDispatchQueue : TrikotDispatchQueue {
    override fun isSerial() = true
    override fun dispatch(block: DispatchBlock) = block()
}

@Deprecated("Streams subscription concurrency is now handled by a serial queue in PublishSubject")
actual class SerialSubscriptionDispatchQueue : OperationDispatchQueue(), TrikotDispatchQueue
