package com.mirego.trikot.streams.reactive

import kotlin.test.Test
import kotlin.test.assertEquals

class SharedExecutionPublisherTests {
    @Test
    fun openSubscriptionInnerPublisherIsSameWhenNotCompleted() {
        var blockInvokeCount = 0
        val publisher = SharedExecutionPublisher {
            blockInvokeCount++
            Publishers.behaviorSubject(blockInvokeCount)
        }
        publisher.subscribe()
        assertEquals(1, publisher.get())
        assertEquals(1, publisher.get())
        assertEquals(1, blockInvokeCount)
    }

    @Test
    fun closedSubscriptionsInnerPublisherIsDifferentWhenSubscribingAndUnsubscribing() {
        var blockInvokeCount = 0
        val publisher = SharedExecutionPublisher {
            blockInvokeCount++
            Publishers.behaviorSubject(blockInvokeCount)
        }
        assertEquals(1, publisher.get())
        assertEquals(2, publisher.get())
        assertEquals(3, blockInvokeCount)
    }

    @Test
    fun closedSubscriptionInnerPublisherInvokedOneTimeEachSubscriptionPlusOneForInitialValue() {
        var blockInvokeCount = 0
        val publisher = SharedExecutionPublisher {
            blockInvokeCount++
            blockInvokeCount.just()
        }
        assertEquals(1, publisher.get())
        assertEquals(2, publisher.get())
        assertEquals(3, blockInvokeCount)
    }

    @Test
    fun openSubscriptionInnerPublisherWhenInErrorInvokedOneTimeEachSubscriptionPlusOneForInitialValue() {
        var blockInvokeCount = 0
        val error = Throwable()
        val publisher = SharedExecutionPublisher {
            blockInvokeCount++
            Publishers.error<Int>(error)
        }
        publisher.subscribe()
        publisher.assertError(error)
        publisher.assertError(error)
        assertEquals(4, blockInvokeCount)
    }

    @Test
    fun closedSubscriptionsInnerPublisherWhenInErrorInvokedOneTimeEachSubscriptionPlusOneForInitialValue() {
        var blockInvokeCount = 0
        val error = Throwable()
        val publisher = SharedExecutionPublisher {
            blockInvokeCount++
            Publishers.error<Int>(error)
        }
        publisher.assertError(error)
        publisher.assertError(error)
        assertEquals(3, blockInvokeCount)
    }
}
