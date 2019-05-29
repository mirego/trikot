package com.mirego.trikot.streams.concurrent.dispatchQueue

import platform.Foundation.NSOperation
import platform.Foundation.NSOperationQueue
import kotlin.native.concurrent.freeze

open class iOSDispatchQueue(maxConcurrentOperation: Long = 4) : DispatchQueue {
    val operationQueue = NSOperationQueue()

    init {
        operationQueue.maxConcurrentOperationCount = maxConcurrentOperation
    }

    override fun dispatch(block: DispatchBlock) {
        block.freeze()
        operationQueue.addOperation(object : NSOperation() {
            override fun isAsynchronous(): Boolean {
                return true
            }
            override fun start() {
                super.start()
                initRuntimeIfNeeded()
                block()
            }
        })
    }
}
