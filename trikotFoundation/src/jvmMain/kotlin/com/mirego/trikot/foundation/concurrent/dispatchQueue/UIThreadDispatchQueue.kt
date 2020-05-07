package com.mirego.trikot.foundation.concurrent.dispatchQueue

actual class UIThreadDispatchQueue actual constructor() :
    DispatchQueue {
    override fun dispatch(block: DispatchBlock) {
        block()
    }
}
