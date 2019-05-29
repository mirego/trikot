package com.mirego.trikot.streams.reactive.connectable

import com.mirego.trikot.streams.cancelable.Cancelable
import com.mirego.trikot.streams.cancelable.CancelableManager
import com.mirego.trikot.streams.concurrent.AtomicReference
import com.mirego.trikot.streams.reactive.SimplePublisher
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher

typealias ConnectablePublisherExecutionBlock<T> = (currentValue: T?) -> Publisher<T>

class ConnectablePublisher<T>(private val executionBlock: ConnectablePublisherExecutionBlock<T>, value: T? = null) : SimplePublisher<T>(value) {
    private val isConnectedRef = AtomicReference(false)

    fun connect(): Cancelable {
        if (!isConnectedRef.compareAndSet(false, true)) {
            throw AlreadyConnectedException()
        }

        val cancelableManager = CancelableManager()
        executionBlock(value).subscribe(cancelableManager) { executionValue -> value = executionValue }
        cancelableManager.add { isConnectedRef.setOrThrow(true, false) }

        return cancelableManager
    }
}
