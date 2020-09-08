package com.mirego.trikot.foundation.concurrent.dispatchQueue

import com.mirego.trikot.foundation.concurrent.freeze
import kotlin.native.concurrent.AtomicInt
import platform.Foundation.NSThread
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual class UIThreadDispatchQueue actual constructor() : DispatchQueue {
    private val count = AtomicInt(0)

    override fun isSerial() = true

    override fun dispatch(block: DispatchBlock) {
        val currentCount = count.addAndGet(1)
        if (currentCount == 1 && NSThread.isMainThread) {
            runQueueTask(block)
        } else {
            freeze(block)
            dispatch_async(dispatch_get_main_queue(), freeze {
                runQueueTask(block)
            })
        }
    }

    private fun runQueueTask(block: DispatchBlock) {
        block()
        count.decrement()
    }
}
