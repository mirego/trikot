package com.mirego.trikot.streams

import com.mirego.trikot.streams.concurrent.AtomicReference
import com.mirego.trikot.streams.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.streams.concurrent.dispatchQueue.OperationDispatchQueue
import com.mirego.trikot.streams.concurrent.dispatchQueue.SerialSubscriptionDispatchQueue

object Configuration {
    private val internalOperationDispatchQueue = AtomicReference<DispatchQueue>(OperationDispatchQueue())
    private val internalSerialSubscriptionDispatchQueue = AtomicReference<DispatchQueue>(SerialSubscriptionDispatchQueue())

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
