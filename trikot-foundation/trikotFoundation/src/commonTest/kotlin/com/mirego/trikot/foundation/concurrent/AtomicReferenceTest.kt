package com.mirego.trikot.foundation.concurrent

import kotlin.test.Test
import kotlin.test.assertEquals

class AtomicReferenceTest {
    private val atomicReference = AtomicReference<String?>(null)
    private val expectedValue = "expectedValue"

    @Test
    fun canSwapValue() {
        assertEquals(expectedValue, atomicReference.compareAndSwap(null, expectedValue))
        assertEquals(null, atomicReference.compareAndSwap(expectedValue, null))
        assertEquals(expectedValue, atomicReference.compareAndSwap(null, expectedValue))
        assertEquals(expectedValue, atomicReference.compareAndSwap(null, "other"))
        assertEquals("other", atomicReference.compareAndSwap(expectedValue, "other"))
    }
}
