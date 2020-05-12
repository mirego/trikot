@file:Suppress("BooleanLiteralArgument")

package com.mirego.trikot.streams.reactive.promise

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.observeOn
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

class Promise<T> internal constructor(
    upstream: Publisher<T>,
    cancellableManager: CancellableManager? = null
) : Publisher<T> {

    private val result = BehaviorSubjectImpl<T>()
    private val serialQueue = SynchronousSerialQueue()

    private val isCancelled: AtomicReference<Boolean> = AtomicReference(false)
    private val internalCancellableManager: CancellableManager = CancellableManager().also {
        cancellableManager?.add(it)
    }

    init {
        upstream
            .observeOn(serialQueue)
            .subscribe(internalCancellableManager,
                onNext = { value ->
                    if (!isCancelled.value) {
                        result.value = value
                        result.complete()

                        internalCancellableManager.cancel()
                    }
                },
                onError = { error ->
                    if (!isCancelled.value) {
                        result.error = error

                        internalCancellableManager.cancel()
                    }
                },
                onCompleted = {
                    if (!isCancelled.value) {
                        if (result.value == null && result.error == null) {
                            result.error = EmptyPromiseException
                        }
                        internalCancellableManager.cancel()
                    }
                }
            )

        internalCancellableManager.add {
            serialQueue.dispatch {
                isCancelled.setOrThrow(false, true)

                if (result.value == null && result.error == null) {
                    result.error = CancelledPromiseException
                }
            }
        }
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

        subscribe(cancellableManager,
            onNext = { t ->
                onSuccess(t).subscribe(cancellableManager,
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
                onError(e).subscribe(cancellableManager,
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
        fun <T> from(
            single: Publisher<T>,
            cancellableManager: CancellableManager? = null
        ): Promise<T> = Promise(single, cancellableManager)

        fun <T> resolve(value: T): Promise<T> = from(Publishers.just(value))

        fun <T> reject(throwable: Throwable): Promise<T> = from(Publishers.error(throwable))
    }
}
