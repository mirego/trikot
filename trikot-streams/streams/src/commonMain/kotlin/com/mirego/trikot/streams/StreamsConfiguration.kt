package com.mirego.trikot.streams

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.OperationDispatchQueue
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import com.mirego.trikot.foundation.concurrent.dispatchQueue.TrikotDispatchQueue

object StreamsConfiguration {
    private val internalOperationDispatchQueue = AtomicReference<TrikotDispatchQueue>(
        OperationDispatchQueue()
    )
    private val internalSerialSubscriptionDispatchQueue =
        AtomicReference<TrikotDispatchQueue>(
            SynchronousSerialQueue()
        )

    var publisherExecutionDispatchQueue: TrikotDispatchQueue
        get() {
            return internalOperationDispatchQueue.value
        }
        set(value) {
            internalOperationDispatchQueue.setOrThrow(internalOperationDispatchQueue.value, value)
        }

    var serialSubscriptionDispatchQueue: TrikotDispatchQueue
        get() {
            return internalSerialSubscriptionDispatchQueue.value
        }
        set(value) {
            internalSerialSubscriptionDispatchQueue.setOrThrow(internalSerialSubscriptionDispatchQueue.value, value)
        }
}
