package com.mirego.trikot.foundation.concurrent.dispatchQueue

actual class OperationDispatchQueue : IOSGlobalDispatchQueue(), TrikotDispatchQueue

@Deprecated("Streams subscription concurrency is now handled by a serial queue in PublishSubject")
actual class SerialSubscriptionDispatchQueue :
    IOSSerialDispatchQueue("serialSubscriptionQueue"),
    TrikotDispatchQueue
