package com.mirego.trikot.foundation.concurrent.dispatchQueue

actual class UIThreadDispatchQueue : TrikotDispatchQueue {
    override fun dispatch(block: DispatchBlock) {
        block()
    }
}
