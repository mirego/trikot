package com.mirego.trikot.streams.reactive

import kotlin.test.Test

class PublishersTests {

    @Test
    fun testJust() {
        val value = 22

        Publishers.just(value).verify(
            value = value,
            error = null,
            completed = true
        )
    }

    @Test
    fun testEmpty() {
        Publishers.empty<Int>().verify(
            value = null,
            error = null,
            completed = true
        )
    }

    @Test
    fun testNever() {
        Publishers.never<Int>().verify(
            value = null,
            error = null,
            completed = false
        )
    }

    @Test
    fun testError() {
        val throwable = Throwable()

        Publishers.error<Int>(throwable).verify(
            value = null,
            error = throwable,
            completed = false
        )
    }
}
