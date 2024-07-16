package com.mirego.trikot.foundation.concurrent.dispatchQueue

actual open class OperationDispatchQueue : TrikotDispatchQueue {
    actual override fun isSerial() = true
    actual override fun dispatch(block: DispatchBlock) = block()
}
