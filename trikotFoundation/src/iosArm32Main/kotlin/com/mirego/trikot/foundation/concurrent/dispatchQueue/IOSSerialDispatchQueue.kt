package com.mirego.trikot.foundation.concurrent.dispatchQueue

import com.mirego.trikot.foundation.concurrent.freeze
import platform.darwin.dispatch_async
import platform.darwin.dispatch_queue_create
import platform.darwin.dispatch_queue_serial_t

open class IOSSerialDispatchQueue(identifier: String) : DispatchQueue {
    private val serialQueue = dispatch_queue_create(
        "com.mirego.trikot.foundation.serial_dispatch_queue.$identifier",
        dispatch_queue_serial_t()
    )

    override fun isSerial() = true

    override fun dispatch(block: DispatchBlock) {
        freeze(block)

        dispatch_async(serialQueue, freeze {
            runQueueTask(block)
        })
    }

    private fun runQueueTask(block: DispatchBlock) {
        block()
    }
}
