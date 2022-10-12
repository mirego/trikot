package com.mirego.trikot.datasources

import com.mirego.trikot.datasources.extensions.value
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import com.mirego.trikot.streams.reactive.RefreshablePublisher
import com.mirego.trikot.streams.reactive.RefreshablePublisherExecutionBlock

class DataStateRefreshablePublisher<T>(
    executionBlock: RefreshablePublisherExecutionBlock<DataState<T, Throwable>>,
    initialValue: DataState<T, Throwable>? = null,
    serialQueue: SynchronousSerialQueue = SynchronousSerialQueue()
) : RefreshablePublisher<DataState<T, Throwable>>(executionBlock, initialValue, serialQueue) {
    override fun refresh() {
        value = value?.let { DataState.pending(it.value()) }
        shouldRefresh.compareAndSet(expected = false, new = true)
        if (hasSubscriptions) {
            doExecuteBlock()
        }
    }
}
