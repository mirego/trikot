package com.mirego.trikot.foundation.concurrent.dispatchQueue

actual class UIThreadDispatchQueue actual constructor() :
    TrikotDispatchQueue {
    override fun dispatch(block: DispatchBlock) {
        block()
    }
}
