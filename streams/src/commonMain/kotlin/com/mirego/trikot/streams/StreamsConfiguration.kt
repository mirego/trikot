package com.mirego.trikot.streams

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.foundation.concurrent.dispatchQueue.OperationDispatchQueue
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SerialSubscriptionDispatchQueue

object StreamsConfiguration {
    private val internalOperationDispatchQueue = AtomicReference<DispatchQueue>(
        OperationDispatchQueue()
    )
    private val internalSerialSubscriptionDispatchQueue =
        AtomicReference<DispatchQueue>(
            SerialSubscriptionDispatchQueue()
        )

    var publisherExecutionDispatchQueue: DispatchQueue
        get() {
            return internalOperationDispatchQueue.value
        }
        set(value) {
            internalOperationDispatchQueue.setOrThrow(internalOperationDispatchQueue.value, value)
        }

    var serialSubscriptionDispatchQueue: DispatchQueue
        get() {
            return internalSerialSubscriptionDispatchQueue.value
        }
        set(value) {
            internalSerialSubscriptionDispatchQueue.setOrThrow(internalSerialSubscriptionDispatchQueue.value, value)
        }
}
