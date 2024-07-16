package com.mirego.trikot.foundation.concurrent.dispatchQueue

actual class UIThreadDispatchQueue : TrikotDispatchQueue {
    actual override fun dispatch(block: DispatchBlock) {
        block()
    }
}
