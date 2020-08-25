package com.mirego.trikot.foundation.concurrent

import kotlin.test.Test
import kotlin.native.concurrent.freeze
import kotlin.native.concurrent.isFrozen
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AtomicPropertyNativeTest {

    private var atomicPropertyNullable: Any? by atomicNullable(Any())
    private var atomicPropertyNullableWithValue: Any by atomic(Any())
    private var atomicPropertyNullableFrozen: Any? by atomicNullable(Any()).freeze()
    private var atomicPropertyNullableWithValueFrozen: Any by atomic(Any()).freeze()

    @Test
    fun testValueIsNeverFrozenByDefault() {
        assertFalse(atomicPropertyNullable.isFrozen)
        atomicPropertyNullable = Any()
        assertFalse(atomicPropertyNullable.isFrozen)

        assertFalse(atomicPropertyNullableWithValue.isFrozen)
        atomicPropertyNullableWithValue = Any()
        assertFalse(atomicPropertyNullableWithValue.isFrozen)
    }

    @Test
    fun testValueIsFrozenIfDelegateIsFrozen() {
        assertTrue(atomicPropertyNullableFrozen.isFrozen)
        atomicPropertyNullableFrozen = Any()
        assertTrue(atomicPropertyNullableFrozen.isFrozen)

        assertTrue(atomicPropertyNullableWithValueFrozen.isFrozen)
        atomicPropertyNullableWithValueFrozen = Any()
        assertTrue(atomicPropertyNullableWithValueFrozen.isFrozen)
    }
}
