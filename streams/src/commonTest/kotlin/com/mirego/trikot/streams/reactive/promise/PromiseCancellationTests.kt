package com.mirego.trikot.streams.reactive.promise

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.verify
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PromiseCancellationTests {

    @Test
    fun promiseCreatedWithCancelledSubscriptionManagerShouldDispatchCancelledError() {
        val cancellableManager = CancellableManager().apply { cancel() }

        Promise.from(Publishers.just(22), cancellableManager)
            .verify(
                value = null,
                error = CancelledPromiseException,
                completed = false
            )
    }

    @Test
    fun completedPromiseShouldNotCancelProvidedCancellableManager() {
        var cancelled = false
        val cancellableManager = CancellableManager().also {
            it.add { cancelled = true }
        }

        Promise.from(Publishers.just(22), cancellableManager)
            .verify(
                value = 22,
                error = null,
                completed = true
            )

        assertFalse(cancelled)
    }

    @Test
    fun cancellingPromiseAfterCreationButBeforeUpstreamDispatchesAValueShouldDispatchCancelledError() {
        val upstream = Publishers.publishSubject<Int>()
        val cancellableManager = CancellableManager()

        val promise = Promise.from(upstream, cancellableManager)
        promise.verify(
            value = null,
            error = null,
            completed = false
        )

        cancellableManager.cancel()
        upstream.value = 22

        promise.verify(
            value = null,
            error = CancelledPromiseException,
            completed = false
        )
    }

    @Test
    fun cancellingPromiseAfterCreationAndAfterUpstreamDispatchesAValueShouldDispatchThatValue() {
        val upstream = Publishers.publishSubject<Int>()
        val cancellableManager = CancellableManager()

        val promise = Promise.from(upstream, cancellableManager)
        promise.verify(
            value = null,
            error = null,
            completed = false
        )

        upstream.value = 22
        cancellableManager.cancel()

        promise.verify(
            value = 22,
            error = null,
            completed = true
        )
    }

    @Test
    fun cancellingPromiseShouldNotCancelFollowingChainedPromises() {
        val upstream = Publishers.publishSubject<Int>()
        val cancellableManager = CancellableManager()

        val promise = Promise.from(upstream, cancellableManager)
        promise.verify(
            value = null,
            error = null,
            completed = false
        )

        cancellableManager.cancel()

        promise.verify(
            value = null,
            error = CancelledPromiseException,
            completed = false
        )

        promise
            .onErrorReturn {
                Promise.resolve(22)
            }
            .verify(
                value = 22,
                error = null,
                completed = true
            )
    }

    @Test
    fun cancellingAChainedPromiseShouldNotCancelFollowingChainedPromises() {
        val upstream = Publishers.publishSubject<String>()
        val cancellableManager = CancellableManager()

        Promise.resolve(22)
            .onSuccessReturn {
                Promise.from(upstream, cancellableManager)
            }
            .also {
                cancellableManager.cancel()
            }
            .onErrorReturn {
                Promise.resolve("Recovering from cancelled chained promise")
            }
            .verify(
                value = "Recovering from cancelled chained promise",
                error = null,
                completed = true
            )
    }

    @Test
    fun finallyIsCalledEvenOnCancelledPromise() {
        val upstream = Publishers.publishSubject<String>()
        val cancellableManager = CancellableManager().also { it.cancel() }
        var finallyCalled = false

        Promise.from(upstream, cancellableManager)
            .finally { finallyCalled = true }
            .verify(
                value = null,
                error = CancelledPromiseException,
                completed = false
            )

        assertTrue(finallyCalled)
    }
}
