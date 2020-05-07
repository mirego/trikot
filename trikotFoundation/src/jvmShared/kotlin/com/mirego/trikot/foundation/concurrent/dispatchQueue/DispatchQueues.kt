package com.mirego.trikot.foundation.concurrent.dispatchQueue

actual class OperationDispatchQueue : JvmDispatchQueue(), DispatchQueue

actual class SerialSubscriptionDispatchQueue : JvmDispatchQueue(1), DispatchQueue
