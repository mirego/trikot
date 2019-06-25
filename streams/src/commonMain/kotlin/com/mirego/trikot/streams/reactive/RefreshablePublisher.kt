package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import org.reactivestreams.Publisher

typealias RefreshablePublisherExecutionBlock<T> = (CancellableManager, Boolean) -> Publisher<T>

class RefreshablePublisher<T>(private val executionBlock: RefreshablePublisherExecutionBlock<T>, value: T? = null) : SimplePublisher<T>(value) {
    private val cancellableManagerProvider = CancellableManagerProvider()

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        doExecuteBlock(false)
    }

    override fun onNoSubscription() {
        super.onNoSubscription()
        doCancel()
    }

    fun refresh() {
        doExecuteBlock(true)
    }

    private fun doExecuteBlock(isRefreshing: Boolean) {
        val cancelPreviousAndCreate = cancellableManagerProvider.cancelPreviousAndCreate()
        executionBlock(cancelPreviousAndCreate, isRefreshing).subscribe(cancelPreviousAndCreate) { executionValue ->
            value = executionValue
        }
    }

    private fun doCancel() {
        cancellableManagerProvider.cancelPreviousAndCreate()
    }
}
