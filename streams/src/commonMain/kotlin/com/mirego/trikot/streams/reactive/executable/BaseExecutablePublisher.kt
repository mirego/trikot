package com.mirego.trikot.streams.reactive.executable

import com.mirego.trikot.streams.Configuration
import com.mirego.trikot.streams.cancelable.CancelableManager
import com.mirego.trikot.streams.concurrent.AtomicReference
import com.mirego.trikot.streams.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.streams.reactive.SimplePublisher

abstract class BaseExecutablePublisher<T>(private val executionQueue: DispatchQueue = Configuration.publisherExecutionDispatchQueue) : ExecutablePublisher<T>, SimplePublisher<T>(null) {
    protected val cancelableManager = CancelableManager()
    private val isStarted = AtomicReference(false)

    final override fun execute() {
        if (!isStarted.compareAndSet(false, true)) {
            throw AlreadyStartedException()
        }

        executionQueue.dispatch {
            internalRun(cancelableManager)
        }
    }

    abstract fun internalRun(cancelableManager: CancelableManager)

    override fun cancel() {
        cancelableManager.cancel()
    }

    fun dispatchSuccess(successValue: T) {
        value = successValue
        complete()
    }

    fun dispatchError(error: Throwable) {
        this.error = error
    }
}
