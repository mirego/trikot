package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.concurrent.AtomicReference
import org.reactivestreams.Publisher

typealias ColdPublisherExecutionBlock<T> = (CancellableManager) -> Publisher<T>

class ColdPublisher<T>(private val executionBlock: ColdPublisherExecutionBlock<T>, value: T? = null) : SimplePublisher<T>(value) {
    private val cancelableManagerRef = AtomicReference(CancellableManager())

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        cancelableManagerRef.value.let {
            executionBlock(it).subscribe(it) {
                    executionValue -> value = executionValue
            }
        }
    }

    override fun onNoSubscription() {
        super.onNoSubscription()
        cancelableManagerRef.value.cancel()
        cancelableManagerRef.setOrThrow(cancelableManagerRef.value, CancellableManager())
    }
}
