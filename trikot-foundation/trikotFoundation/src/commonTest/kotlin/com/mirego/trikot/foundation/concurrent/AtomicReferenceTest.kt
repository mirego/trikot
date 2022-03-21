package com.mirego.trikot.foundation.concurrent

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AtomicReferenceTest {
    private val atomicReference = AtomicReference<String?>(null)
    private val expectedValue = "expectedValue"

    @Test
    fun canSetNewNullValueAndReadAfterFreezing() {
        atomicReference.compareAndSet(null, expectedValue)
        assertEquals(expectedValue, atomicReference.value)
        MrFreeze.freeze(atomicReference)
        atomicReference.compareAndSet(expectedValue, null)
        assertNull(atomicReference.value)
    }

    @Test
    fun canSwapValue() {
        assertEquals(expectedValue, atomicReference.compareAndSwap(null, expectedValue))
        assertEquals(null, atomicReference.compareAndSwap(expectedValue, null))
        freeze(atomicReference)
        assertEquals(expectedValue, atomicReference.compareAndSwap(null, expectedValue))
        assertEquals(expectedValue, atomicReference.compareAndSwap(null, "other"))
        assertEquals("other", atomicReference.compareAndSwap(expectedValue, "other"))
    }
}
