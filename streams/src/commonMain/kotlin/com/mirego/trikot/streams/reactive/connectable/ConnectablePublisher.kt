package com.mirego.trikot.streams.reactive.connectable

import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher

typealias ConnectablePublisherExecutionBlock<T> = (currentValue: T?) -> Publisher<T>

class ConnectablePublisher<T>(private val executionBlock: ConnectablePublisherExecutionBlock<T>, value: T? = null) : BehaviorSubjectImpl<T>(value) {
    private val isConnectedRef = AtomicReference(false)

    fun connect(): Cancellable {
        if (!isConnectedRef.compareAndSet(false, true)) {
            throw AlreadyConnectedException()
        }

        val cancellableManager = CancellableManager()
        executionBlock(value).subscribe(cancellableManager) { executionValue -> value = executionValue }
        cancellableManager.add { isConnectedRef.setOrThrow(true, false) }

        return cancellableManager
    }
}
