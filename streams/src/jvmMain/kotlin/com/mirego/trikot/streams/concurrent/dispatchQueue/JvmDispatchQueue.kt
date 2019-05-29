package com.mirego.trikot.streams.concurrent.dispatchQueue

import java.util.concurrent.Executors

open class JvmDispatchQueue(maxConcurrentOperation: Long = 4) : DispatchQueue {
    private var pool = Executors.newFixedThreadPool(maxConcurrentOperation.toInt())

    override fun dispatch(block: DispatchBlock) {
        pool.execute { block() }
    }
}
