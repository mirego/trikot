package com.mirego.trikot.foundation.concurrent

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AtomicPropertyTest {

    private var atomicPropertyNullable: Int? by atomicNullable()
    private var atomicPropertyNullableWithValue: Int? by atomicNullable(1)
    private var atomicPropertyNotNull: Int by atomic(2)

    @Test
    fun testNullableAtomicPropertyNoDefaultValue() {
        assertNull(atomicPropertyNullable)
        atomicPropertyNullable = 1
        assertEquals(atomicPropertyNullable, 1)
        atomicPropertyNullable = null
        assertNull(atomicPropertyNullable)
    }

    @Test
    fun testNullableAtomicPropertyWithValue() {
        assertEquals(atomicPropertyNullableWithValue, 1)
        atomicPropertyNullableWithValue = null
        assertNull(atomicPropertyNullableWithValue)
    }

    @Test
    fun testNotNullAtomicPropertyWithValue() {
        assertEquals(atomicPropertyNotNull, 2)
        atomicPropertyNotNull = 3
        assertEquals(atomicPropertyNotNull, 3)
    }
}
