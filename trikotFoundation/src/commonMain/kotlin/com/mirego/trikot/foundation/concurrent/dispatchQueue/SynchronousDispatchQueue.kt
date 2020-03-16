package com.mirego.trikot.foundation.concurrent.dispatchQueue

class SynchronousDispatchQueue : DispatchQueue {

    override fun isSerial() = false

    override fun dispatch(block: DispatchBlock) = block()
}
