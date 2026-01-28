package com.mirego.trikot.streams.reactive.promise

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.tests.verify
import kotlin.test.Test
import kotlin.test.assertEquals
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

    @Test
    fun completedPromiseIsRemovedFromParentCancellableManager() {
        val cancellableManager = CancellableManager()
        assertEquals(0, cancellableManager.queueListSize)

        val upstream = Publishers.publishSubject<Int>()
        val promise = Promise.from(upstream, cancellableManager)

        // Promise added itself to the parent
        assertEquals(1, cancellableManager.queueListSize)

        // Complete the promise
        upstream.value = 22

        promise.verify(
            value = 22,
            error = null,
            completed = true
        )

        // Promise should have removed itself from the parent
        assertEquals(0, cancellableManager.queueListSize)
    }

    @Test
    fun erroredPromiseIsRemovedFromParentCancellableManager() {
        val cancellableManager = CancellableManager()
        assertEquals(0, cancellableManager.queueListSize)

        val error = Exception("Test error")
        Promise.from(Publishers.error<Int>(error), cancellableManager)
            .verify(
                value = null,
                error = error,
                completed = false
            )

        // Promise should have removed itself from the parent
        assertEquals(0, cancellableManager.queueListSize)
    }

    @Test
    fun emptyCompletedPromiseIsRemovedFromParentCancellableManager() {
        val cancellableManager = CancellableManager()
        assertEquals(0, cancellableManager.queueListSize)

        val upstream = Publishers.publishSubject<Int>()
        val promise = Promise.from(upstream, cancellableManager)

        assertEquals(1, cancellableManager.queueListSize)

        // Complete without emitting a value
        upstream.complete()

        promise.verify(
            value = null,
            error = EmptyPromiseException,
            completed = false
        )

        // Promise should have removed itself from the parent
        assertEquals(0, cancellableManager.queueListSize)
    }

    @Test
    fun multipleCompletedPromisesAreRemovedFromParentCancellableManager() {
        val cancellableManager = CancellableManager()
        assertEquals(0, cancellableManager.queueListSize)

        // Create and complete multiple promises
        for (i in 1..10) {
            Promise.from(Publishers.just(i), cancellableManager)
                .verify(
                    value = i,
                    error = null,
                    completed = true
                )
        }

        // All promises should have removed themselves
        assertEquals(0, cancellableManager.queueListSize)
    }

    @Test
    fun pendingPromiseRemainsInParentCancellableManagerUntilCompleted() {
        val cancellableManager = CancellableManager()
        assertEquals(0, cancellableManager.queueListSize)

        val upstream1 = Publishers.publishSubject<Int>()
        val upstream2 = Publishers.publishSubject<Int>()
        val upstream3 = Publishers.publishSubject<Int>()

        Promise.from(upstream1, cancellableManager)
        assertEquals(1, cancellableManager.queueListSize)

        Promise.from(upstream2, cancellableManager)
        assertEquals(2, cancellableManager.queueListSize)

        Promise.from(upstream3, cancellableManager)
        assertEquals(3, cancellableManager.queueListSize)

        // Complete first promise
        upstream1.value = 1
        assertEquals(2, cancellableManager.queueListSize)

        // Complete second promise
        upstream2.value = 2
        assertEquals(1, cancellableManager.queueListSize)

        // Complete third promise
        upstream3.value = 3
        assertEquals(0, cancellableManager.queueListSize)
    }
}
