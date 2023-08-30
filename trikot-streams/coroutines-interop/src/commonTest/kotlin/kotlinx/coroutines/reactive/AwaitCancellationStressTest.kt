/*
 * Copyright 2016-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.reactive

import kotlinx.atomicfu.locks.reentrantLock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import org.reactivestreams.Publisher
import org.reactivestreams.Subscription
import kotlin.test.Test

/**
 * This test checks implementation of rule 2.7 for await methods - serial execution of subscription methods
 */
class AwaitCancellationStressTest : TestBase() {
    private val iterations = 10_000 * stressTestMultiplier

    @Test
    fun testAwaitCancellationOrder() = runTest {
        repeat(iterations) {
            val job = launch(Dispatchers.Default) {
                testPublisher().awaitFirst()
            }
            job.cancelAndJoin()
        }
    }

    private fun testPublisher() = Publisher { s ->
        val lock = reentrantLock()
        s.onSubscribe(object : Subscription {
            override fun request(n: Long) {
                check(lock.tryLock())
                s.onNext(42)
                lock.unlock()
            }

            override fun cancel() {
                check(lock.tryLock())
                lock.unlock()
            }
        })
    }
}
