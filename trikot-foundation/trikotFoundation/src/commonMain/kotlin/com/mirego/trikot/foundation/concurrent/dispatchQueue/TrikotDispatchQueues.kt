package com.mirego.trikot.foundation.concurrent.dispatchQueue

expect class OperationDispatchQueue() : TrikotDispatchQueue {
    override fun isSerial(): Boolean
    override fun dispatch(block: DispatchBlock)
}
