package com

import com.mirego.trikot.streams.reactive.Publishers
import kotlin.native.concurrent.isFrozen
import kotlin.test.Test
import kotlin.test.assertTrue

class FrozenPublisherTests {

    @Test
    fun frozenBehaviourSubjectsReallyReturnsAFrozenPublisher() {
        val publisher = Publishers.frozenBehaviorSubject("test")
        assertTrue(publisher.isFrozen)
    }

    @Test
    fun frozenPublishSubjectsReallyReturnsAFrozenPublisher() {
        val publisher = Publishers.frozenPublishSubject<String>()
        assertTrue(publisher.isFrozen)
    }
}
