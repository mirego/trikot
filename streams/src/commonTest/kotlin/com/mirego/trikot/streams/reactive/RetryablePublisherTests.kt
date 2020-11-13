package com.mirego.trikot.streams.reactive

import kotlin.test.Test
import kotlin.test.assertEquals

class RetryablePublisherTests {

    @Test
    fun whenCallingRetryBlockIsRecalled() {
        var blockInvokeCount = 0
        val retryablePublisher = RetryablePublisher {
            blockInvokeCount++
            Publishers.behaviorSubject(blockInvokeCount)
        }
        retryablePublisher.subscribe()

        assertEquals(1, retryablePublisher.get())

        retryablePublisher.retry()

        assertEquals(2, retryablePublisher.get())
    }
}
