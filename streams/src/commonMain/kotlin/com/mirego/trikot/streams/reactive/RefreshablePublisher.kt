package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import org.reactivestreams.Publisher

typealias RefreshablePublisherExecutionBlock<T> = (CancellableManager, Boolean) -> Publisher<T>

open class RefreshablePublisher<T>(private val executionBlock: RefreshablePublisherExecutionBlock<T>, value: T? = null, private val serialQueue: SynchronousSerialQueue = SynchronousSerialQueue()) :
    BehaviorSubjectImpl<T>(value, serialQueue) {
    private val cancellableManagerProvider = CancellableManagerProvider()
    protected val shouldRefresh = AtomicReference(false)

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        doExecuteBlock()
    }

    override fun onNoSubscription() {
        super.onNoSubscription()
        doCancel()
    }

    open fun refresh() {
        value = null
        shouldRefresh.compareAndSet(false, true)
        if (hasSubscriptions) {
            doExecuteBlock()
        }
    }

    protected fun doExecuteBlock() {
        serialQueue.dispatch {
            val isRefreshing = shouldRefresh.value
            val cancelPreviousAndCreate = cancellableManagerProvider.cancelPreviousAndCreate()
            executionBlock(
                cancelPreviousAndCreate,
                isRefreshing
            ).subscribe(cancelPreviousAndCreate) { executionValue ->
                value = executionValue
                if (isRefreshing) {
                    shouldRefresh.setOrThrow(shouldRefresh.value, false)
                }
            }
        }
    }

    private fun doCancel() {
        serialQueue.dispatch {
            cancellableManagerProvider.cancelPreviousAndCreate()
        }
    }
}
