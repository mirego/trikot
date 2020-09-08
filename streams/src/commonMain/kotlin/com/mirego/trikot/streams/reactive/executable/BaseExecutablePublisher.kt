package com.mirego.trikot.streams.reactive.executable

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.streams.StreamsConfiguration
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl

abstract class BaseExecutablePublisher<T>(private val executionQueue: DispatchQueue = StreamsConfiguration.publisherExecutionDispatchQueue) :
    ExecutablePublisher<T>, BehaviorSubjectImpl<T>(null) {
    protected val cancellableManager = CancellableManager()
    private val isStarted = AtomicReference(false)

    final override fun execute() {
        if (!isStarted.compareAndSet(false, true)) {
            throw AlreadyStartedException()
        }

        executionQueue.dispatch {
            internalRun(cancellableManager)
        }
    }

    abstract fun internalRun(cancellableManager: CancellableManager)

    override fun cancel() {
        cancellableManager.cancel()
    }

    fun dispatchSuccess(successValue: T) {
        value = successValue
        complete()
    }

    fun dispatchError(error: Throwable) {
        cancel()
        this.error = error
    }

    override fun complete() {
        cancel()
        super.complete()
    }
}
