package com.mirego.trikot.foundation.concurrent.dispatchQueue

actual class UIThreadDispatchQueue : DispatchQueue {
    override fun dispatch(block: DispatchBlock) {
        block()
    }
}
