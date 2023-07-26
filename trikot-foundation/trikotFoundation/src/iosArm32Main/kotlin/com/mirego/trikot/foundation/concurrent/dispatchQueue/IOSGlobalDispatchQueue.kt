package com.mirego.trikot.foundation.concurrent.dispatchQueue

import kotlinx.cinterop.ExperimentalForeignApi
import platform.darwin.DISPATCH_QUEUE_PRIORITY_HIGH
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_global_queue

open class IOSGlobalDispatchQueue : TrikotDispatchQueue {
    override fun isSerial() = false

    @OptIn(ExperimentalForeignApi::class)
    override fun dispatch(block: DispatchBlock) {
        dispatch_async(
            dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0u),
            block
        )
    }
}
