/*
 * Copyright 2016-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.shared
import com.mirego.trikot.streams.reactive.subscribe
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withTimeout
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.test.Test
import kotlin.test.assertTrue

class FlowAsPublisherTest : TestBase() {
    @Test
    fun testErrorOnCancellationIsReported() {
        expect(1)
        flow {
            try {
                emit(2)
            } finally {
                expect(3)
                throw TestException()
            }
        }.asPublisher().subscribe(object : Subscriber<Int> {
            private lateinit var subscription: Subscription

            override fun onComplete() {
                expectUnreached()
            }

            override fun onSubscribe(s: Subscription) {
                subscription = s
                subscription.request(2)
            }

            override fun onNext(t: Int) {
                expect(t)
                subscription.cancel()
            }

            override fun onError(t: Throwable) {
                assertTrue(t is TestException)
                expect(4)
            }
        })
        finish(5)
    }

    @Test
    fun testCancellationIsNotReported() {
        expect(1)
        flow {
            emit(2)
        }.asPublisher().subscribe(object : Subscriber<Int> {
            private lateinit var subscription: Subscription

            override fun onComplete() {
                expectUnreached()
            }

            override fun onSubscribe(s: Subscription) {
                subscription = s
                subscription.request(2)
            }

            override fun onNext(t: Int) {
                expect(t)
                subscription.cancel()
            }

            override fun onError(t: Throwable) {
                expectUnreached()
            }
        })
        finish(3)
    }

    @Test
    fun testFlowWithSharedResultIsShared() = runTest {
        val publisher = flowOf(1)
            .map { n ->
                expect(1)
                n
            }
            .asPublisher()
            .shared()

        publisher.subscribe(CancellableManager()) {}
        publisher.subscribe(CancellableManager()) {}

        finish(2)
    }

    @Test
    fun testFlowWithTimeout() = runTest {
        val publisher = flow<Int> {
            expect(2)
            withTimeout(1) { delay(Long.MAX_VALUE) }
        }.asPublisher()
        try {
            expect(1)
            publisher.awaitFirstOrNull()
        } catch (e: CancellationException) {
            expect(3)
        }
        finish(4)
    }

    class TestException : Exception()
}
