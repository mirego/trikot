package com.mirego.trikot.streams.concurrent.dispatchQueue

actual class OperationDispatchQueue : JvmDispatchQueue(), DispatchQueue

actual class SerialSubscriptionDispatchQueue : JvmDispatchQueue(1), DispatchQueue
