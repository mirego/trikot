package com.mirego.trikot.foundation.concurrent.dispatchQueue

class SynchronousDispatchQueue : DispatchQueue {

    override fun isSerial() = true

    override fun dispatch(block: DispatchBlock) = block()
}
