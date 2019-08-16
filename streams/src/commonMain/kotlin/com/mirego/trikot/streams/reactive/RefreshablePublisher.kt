package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.foundation.concurrent.AtomicReference
import org.reactivestreams.Publisher

typealias RefreshablePublisherExecutionBlock<T> = (CancellableManager, Boolean) -> Publisher<T>

class RefreshablePublisher<T>(private val executionBlock: RefreshablePublisherExecutionBlock<T>, value: T? = null) : BehaviorSubjectImpl<T>(value) {
    private val cancellableManagerProvider = CancellableManagerProvider()
    private val shouldRefresh = AtomicReference(false)

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        doExecuteBlock(shouldRefresh.value)
    }

    override fun onNoSubscription() {
        super.onNoSubscription()
        doCancel()
    }

    fun refresh() {
        value = null
        if (hasSubscriptions) {
            doExecuteBlock(true)
        } else {
            shouldRefresh.setOrThrow(shouldRefresh.value, true)
        }
    }

    private fun doExecuteBlock(isRefreshing: Boolean) {
        val cancelPreviousAndCreate = cancellableManagerProvider.cancelPreviousAndCreate()
        executionBlock(cancelPreviousAndCreate, isRefreshing).subscribe(cancelPreviousAndCreate) { executionValue ->
            value = executionValue
            if (isRefreshing) {
                shouldRefresh.setOrThrow(shouldRefresh.value, false)
            }
        }
    }

    private fun doCancel() {
        cancellableManagerProvider.cancelPreviousAndCreate()
    }
}
