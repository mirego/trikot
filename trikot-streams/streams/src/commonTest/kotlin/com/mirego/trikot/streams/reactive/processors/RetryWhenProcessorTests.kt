package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.ColdPublisher
import com.mirego.trikot.streams.reactive.MockPublisher
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.retryWhen
import com.mirego.trikot.streams.reactive.subscribe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RetryWhenProcessorTests {
    companion object {
        val ERROR_1 = Throwable()
        val ERROR_2 = Throwable()
    }

    @Test
    fun retryWhen_simple() {
        var receivedErrorRetryWhen: Throwable? = null
        var receivedValueSubscription: Int? = null
        var receivedErrorSubscription: Throwable? = null
        val attempt = AtomicReference(1)
        val originalPublisher = ColdPublisher {
            when (val value = attempt.value) {
                1 -> Publishers.error(ERROR_1)
                else -> value.just()
            }.also {
                attempt.setOrThrow(attempt.value, attempt.value + 1)
            }
        }

        originalPublisher.retryWhen { errors ->
            errors.map {
                receivedErrorRetryWhen = it
            }
        }.subscribe(
            CancellableManager(),
            onNext = {
                receivedValueSubscription = it
            },
            onError = {
                receivedErrorSubscription = it
            }
        )

        assertNull(receivedErrorSubscription)
        assertEquals(ERROR_1, receivedErrorRetryWhen)
        assertEquals(2, receivedValueSubscription)
    }

    @Test
    fun retryWhen_completion() {
        var receivedErrorSubscription: Throwable? = null
        var receivedValueSubscription: Int? = null
        var receivedOnComplete: Boolean = false
        val attempt = AtomicReference(1)
        val originalPublisher = ColdPublisher {
            attempt.value.just()
                .also {
                    attempt.setOrThrow(attempt.value, attempt.value + 1)
                }
        }
        originalPublisher.retryWhen { errors -> errors }.subscribe(
            CancellableManager(),
            onNext = {
                receivedValueSubscription = it
            },
            onError = {
                receivedErrorSubscription = it
            },
            onCompleted = {
                receivedOnComplete = true
            }
        )

        assertNull(receivedErrorSubscription)
        assertEquals(1, receivedValueSubscription)
        assertTrue(receivedOnComplete)
    }

    @Test
    fun retryWhen_cancellation() {
        val errorPublisher = MockPublisher()
        val attempt = AtomicReference(1)
        val originalPublisher = ColdPublisher {
            attempt.value.just()
                .also {
                    attempt.setOrThrow(attempt.value, attempt.value + 1)
                }
        }
        val cancellableManager = CancellableManager()
        originalPublisher.retryWhen {
            errorPublisher
        }.subscribe(cancellableManager) {}
        cancellableManager.cancel()

        assertFalse(errorPublisher.getHasSubscriptions)
    }

    @Test
    fun retryWhen_error() {
        var receivedErrorSubscription: Throwable? = null
        var receivedValueSubscription: Int? = null
        val originalPublisher = ColdPublisher {
            Publishers.error<Int>(ERROR_1)
        }

        originalPublisher.retryWhen { Publishers.error(ERROR_2) }.subscribe(
            CancellableManager(),
            onNext = {
                receivedValueSubscription = it
            },
            onError = {
                receivedErrorSubscription = it
            }
        )

        assertEquals(ERROR_2, receivedErrorSubscription)
        assertNull(receivedValueSubscription)
    }
}
