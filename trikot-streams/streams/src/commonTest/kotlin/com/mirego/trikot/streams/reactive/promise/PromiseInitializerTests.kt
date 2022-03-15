package com.mirego.trikot.streams.reactive.promise

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.startWith
import com.mirego.trikot.streams.tests.verify
import kotlin.test.Test

class PromiseInitializerTests {

    @Test
    fun testFromSingleValuePublisher() {
        val value = 22

        Promise.from(Publishers.just(value))
            .verify(
                value = value,
                error = null,
                completed = true
            )
    }

    @Test
    fun testFromErrorPublisher() {
        val throwable = Throwable()

        Promise.from<Int>(Publishers.error(throwable))
            .verify(
                value = null,
                error = throwable,
                completed = false
            )
    }

    @Test
    fun testFromEmptyPublisherShouldThrowEmptyPromiseException() {
        Promise.from<Int>(Publishers.empty())
            .verify(
                value = null,
                error = EmptyPromiseException,
                completed = false
            )
    }

    @Test
    fun testFromNeverPublisherShouldNeverCallback() {
        Promise.from<Int>(Publishers.never())
            .verify(
                value = null,
                error = null,
                completed = false
            )
    }

    @Test
    fun testFromMultipleValuesPublisherShouldOnlyDispatchFirstValue() {
        Promise.from(Publishers.just(23).startWith(22))
            .verify(
                value = 22,
                error = null,
                completed = true
            )
    }

    @Test
    fun testFromCancelledCancellableManager() {
        val cancellableManager = CancellableManager()
        cancellableManager.cancel()

        Promise.from(Publishers.just(22), cancellableManager)
            .verify(
                value = null,
                error = CancelledPromiseException,
                completed = false
            )
    }

    @Test
    fun testResolve() {
        val value = 22

        Promise.resolve(22)
            .verify(
                value = value,
                error = null,
                completed = true
            )
    }

    @Test
    fun testReject() {
        val throwable = Throwable()

        Promise.reject<String>(throwable)
            .verify(
                value = null,
                error = throwable,
                completed = false
            )
    }

    @Test
    fun testCreateWithReject() {
        val throwable = Throwable()

        Promise.create<String> { _, reject ->
            reject(throwable)
        }
            .verify(
                value = null,
                error = throwable,
                completed = false
            )
    }

    @Test
    fun testCreateWithResolve() {
        val value = 22

        Promise.create<Int> { resolve, _ ->
            resolve(value)
        }
            .verify(
                value = value,
                error = null,
                completed = true
            )
    }

    @Test
    fun testCreateWithCancelledCancellableManager() {
        val cancellableManager = CancellableManager()
        cancellableManager.cancel()

        Promise.create<Int>(cancellableManager) { resolve, _ ->
            resolve(22)
        }
            .verify(
                value = null,
                error = CancelledPromiseException,
                completed = false
            )
    }
}
