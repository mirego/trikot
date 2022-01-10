package com.mirego.trikot.foundation.concurrent.dispatchQueue

import android.os.Handler
import android.os.Looper
import java.util.concurrent.atomic.AtomicInteger

actual class UIThreadDispatchQueue actual constructor() : TrikotDispatchQueue {
    private val count: AtomicInteger = AtomicInteger()

    private val mainLooperHandler: Handler = Handler(Looper.getMainLooper())

    override fun isSerial() = true

    override fun dispatch(block: DispatchBlock) {
        val currentCount: Int = count.incrementAndGet()
        if (Looper.myLooper() == Looper.getMainLooper() && currentCount == 1) {
            runQueueTask(block)
        } else {
            mainLooperHandler.post { runQueueTask(block) }
        }
    }

    private fun runQueueTask(block: DispatchBlock) {
        block()
        count.decrementAndGet()
    }
}
