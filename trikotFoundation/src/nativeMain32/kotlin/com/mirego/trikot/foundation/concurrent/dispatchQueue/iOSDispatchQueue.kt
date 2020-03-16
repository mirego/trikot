package com.mirego.trikot.foundation.concurrent.dispatchQueue

import platform.Foundation.NSOperation
import platform.Foundation.NSOperationQueue
import kotlin.native.concurrent.freeze

open class iOSDispatchQueue(private val maxConcurrentOperation: Long = 4) : DispatchQueue {

    override fun isSerial() = maxConcurrentOperation == 1L

    val operationQueue = NSOperationQueue()

    init {
        operationQueue.maxConcurrentOperationCount = maxConcurrentOperation.toInt()
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
