package com.mirego.trikot.foundation.concurrent.dispatchQueue

class SynchronousDispatchQueue : TrikotDispatchQueue {

    override fun isSerial() = false

    override fun dispatch(block: DispatchBlock) = block()
}
