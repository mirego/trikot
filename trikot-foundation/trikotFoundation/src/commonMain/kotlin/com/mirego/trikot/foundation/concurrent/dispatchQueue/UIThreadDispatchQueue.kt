package com.mirego.trikot.foundation.concurrent.dispatchQueue

expect class UIThreadDispatchQueue() : TrikotDispatchQueue {
    override fun isSerial(): Boolean
    override fun dispatch(block: DispatchBlock)
}
