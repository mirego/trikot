package com.mirego.trikot.foundation.concurrent.dispatchQueue

expect class OperationDispatchQueue() : TrikotDispatchQueue

@Deprecated("Streams subscription concurrency is now handled by a serial queue in PublishSubject")
expect class SerialSubscriptionDispatchQueue() : TrikotDispatchQueue
