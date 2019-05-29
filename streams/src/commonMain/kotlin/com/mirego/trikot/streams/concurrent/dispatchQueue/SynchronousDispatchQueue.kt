package com.mirego.trikot.streams.concurrent.dispatchQueue

class SynchronousDispatchQueue : DispatchQueue {
    override fun dispatch(block: DispatchBlock) {
        block()
    }
}
