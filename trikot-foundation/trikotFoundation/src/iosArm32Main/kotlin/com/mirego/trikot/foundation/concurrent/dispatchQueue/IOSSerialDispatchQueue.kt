package com.mirego.trikot.foundation.concurrent.dispatchQueue

import platform.darwin.DISPATCH_QUEUE_SERIAL
import platform.darwin.dispatch_async
import platform.darwin.dispatch_queue_create
import platform.darwin.dispatch_queue_t

open class IOSSerialDispatchQueue(identifier: String) : TrikotDispatchQueue {
    private val serialQueue = dispatch_queue_create(
        "com.mirego.trikot.foundation.serial_dispatch_queue.$identifier",
        DISPATCH_QUEUE_SERIAL as dispatch_queue_t
    )

    override fun isSerial() = true

    override fun dispatch(block: DispatchBlock) {
        dispatch_async(
            serialQueue,
            block
        )
    }
}
