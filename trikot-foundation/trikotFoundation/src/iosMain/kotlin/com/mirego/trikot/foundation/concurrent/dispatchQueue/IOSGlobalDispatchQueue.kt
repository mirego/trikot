package com.mirego.trikot.foundation.concurrent.dispatchQueue

import platform.darwin.DISPATCH_QUEUE_PRIORITY_HIGH
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_global_queue

open class IOSGlobalDispatchQueue : TrikotDispatchQueue {
    override fun isSerial() = false

    override fun dispatch(block: DispatchBlock) {
        dispatch_async(
            dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH.toLong(), 0UL),
            block
        )
    }
}
