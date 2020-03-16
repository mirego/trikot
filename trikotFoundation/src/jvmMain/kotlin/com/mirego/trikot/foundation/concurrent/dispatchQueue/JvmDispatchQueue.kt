package com.mirego.trikot.foundation.concurrent.dispatchQueue

import java.util.concurrent.Executors

open class JvmDispatchQueue(private val maxConcurrentOperation: Long = 4) : DispatchQueue {

    override fun isSerial() = maxConcurrentOperation == 1L

    private var pool = Executors.newFixedThreadPool(maxConcurrentOperation.toInt())

    override fun dispatch(block: DispatchBlock) = pool.execute { block() }
}
