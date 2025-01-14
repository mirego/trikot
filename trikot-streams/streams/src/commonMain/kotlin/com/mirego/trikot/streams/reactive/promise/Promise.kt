@file:Suppress("BooleanLiteralArgument")

package com.mirego.trikot.streams.reactive.promise

import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.cancellable.VerifiableCancelledState
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.observeOn
import com.mirego.trikot.streams.reactive.subscribe
import kotlinx.atomicfu.atomic
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

class Promise<T> internal constructor(
    upstream: Publisher<T>,
    cancellableManager: CancellableManager? = null
) : Publisher<T> {

    private val serialQueue = SynchronousSerialQueue()
    private val result = BehaviorSubjectImpl<T>(serialQueue = serialQueue)

    private val internalCancellableManager: CancellableManager = CancellableManager()

    private val onParentCancellation = object : Cancellable, VerifiableCancelledState {
        override var isCancelled: Boolean by atomic(false)

        override fun cancel() {
            serialQueue.dispatch {
                isCancelled = true
                internalCancellableManager.cancel()

                if (result.value == null && result.error == null) {
                    result.error = CancelledPromiseException
                }
            }
        }
    }

    private val isParentCancellableCancelled: Boolean
        get() = onParentCancellation.isCancelled

    /**
     * When a result is received, we want to make sure we're considered as "cancelled" by the provided cancellableManager if any.
     * This allows to use `CancellableManager::cleanCancelledChildren` with promises to clean up finished promises
     * and avoids keeping a reference on the promise, which is finished anyway. No further results will be emitted.
     *
     * We don't want the `onParentCancellation::cancel()` to run since the provided cancellableManager was not cancelled,
     * so we don't call `cancel()` directly, but instead modify the `isCancelled` value manually.
      */
    private fun onResultReceived() {
        onParentCancellation.isCancelled = true
        internalCancellableManager.cancel()
    }

    init {
        cancellableManager?.add(onParentCancellation)

        upstream
            .observeOn(serialQueue)
            .subscribe(
                internalCancellableManager,
                onNext = { value ->
                    if (!isParentCancellableCancelled) {
                        result.value = value
                        result.complete()

                        onResultReceived()
                    }
                },
                onError = { error ->
                    if (!isParentCancellableCancelled) {
                        result.error = error

                        onResultReceived()
                    }
                },
                onCompleted = {
                    if (!isParentCancellableCancelled) {
                        if (result.value == null && result.error == null) {
                            result.error = EmptyPromiseException
                        }

                        onResultReceived()
                    }
                }
            )
    }

    override fun subscribe(s: Subscriber<in T>) {
        result.subscribe(s)
    }

    fun onSuccess(accept: (T) -> Unit): Promise<T> = thenReturn(
        onSuccess = { accept(it); resolve(it) },
        onError = ::reject
    )

    fun <R> onSuccessReturn(apply: (T) -> Promise<R>): Promise<R> = thenReturn(
        onSuccess = apply,
        onError = ::reject
    )

    fun onError(accept: (Throwable) -> Unit): Promise<T> = thenReturn(
        onSuccess = ::resolve,
        onError = { accept(it); reject(it) }
    )

    fun onErrorReturn(apply: (Throwable) -> Promise<T>): Promise<T> = thenReturn(
        onSuccess = ::resolve,
        onError = apply
    )

    fun finally(execute: () -> Unit): Promise<T> = thenReturn(
        onSuccess = { execute(); resolve(it) },
        onError = { execute(); reject(it) }
    )

    fun <R> finallyReturn(supply: () -> Promise<R>): Promise<R> = thenReturn(
        onSuccess = { supply() },
        onError = { supply() }
    )

    fun then(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit): Promise<T> = thenReturn(
        onSuccess = { onSuccess(it); resolve(it) },
        onError = { onError(it); reject(it) }
    )

    fun <R> thenReturn(onSuccess: (T) -> Promise<R>, onError: (Throwable) -> Promise<R>): Promise<R> {
        val result = BehaviorSubjectImpl<R>()
        val cancellableManager = CancellableManager()

        subscribe(
            cancellableManager,
            onNext = { t ->
                onSuccess(t).subscribe(
                    cancellableManager,
                    onNext = { r ->
                        result.value = r
                    },
                    onError = { error ->
                        result.error = error
                    },
                    onCompleted = {
                        result.complete()
                    }
                )
            },
            onError = { e ->
                onError(e).subscribe(
                    cancellableManager,
                    onNext = { r ->
                        result.value = r
                    },
                    onError = { error ->
                        result.error = error
                    },
                    onCompleted = {
                        result.complete()
                    }
                )
            }
        )

        return Promise(result)
    }

    companion object {
        fun <T> resolve(value: T): Promise<T> = from(Publishers.just(value))

        fun <T> reject(throwable: Throwable): Promise<T> = from(Publishers.error(throwable))

        fun <T> from(
            single: Publisher<T>,
            cancellableManager: CancellableManager? = null
        ): Promise<T> = Promise(single, cancellableManager)

        fun <T> create(
            cancellableManager: CancellableManager? = null,
            block: (resolve: ((T) -> Unit), reject: ((Throwable) -> Unit)) -> Unit
        ): Promise<T> {
            val deferredResult = Publishers.behaviorSubject<T>()

            block(
                { t -> deferredResult.value = t },
                { e -> deferredResult.error = e }
            )

            return from(deferredResult, cancellableManager)
        }
    }
}
