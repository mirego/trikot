package com

import com.mirego.trikot.foundation.concurrent.MrFreeze
import com.mirego.trikot.foundation.concurrent.dispatchQueue.DispatchBlock
import com.mirego.trikot.foundation.concurrent.dispatchQueue.TrikotDispatchQueue
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.processors.ThreadLocalProcessor
import com.mirego.trikot.streams.reactive.shared
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.native.concurrent.ensureNeverFrozen
import kotlin.native.concurrent.freeze
import kotlin.native.concurrent.isFrozen
import kotlin.test.Test
import kotlin.test.assertFalse

class ThreadLocalProcessorTests {

    @Test
    fun threadLocalSubscribersAreNotFrozenByFrozenParentPublisher() {
        val initialPublisher = Publishers.frozenBehaviorSubject("a")
        val threadLocal =
            ThreadLocalProcessor(initialPublisher, FreezingDispatchQueue(), FreezingDispatchQueue())
        val sharedPublisher = threadLocal.shared().also { MrFreeze.ensureNeverFrozen(it) }
        val mapProcessor = sharedPublisher.map { it + "mapped" }
        val subscriber = object : Subscriber<String> {
            override fun onComplete() {
            }

            override fun onError(t: Throwable) {
            }

            override fun onNext(t: String) {
            }

            override fun onSubscribe(s: Subscription) {
            }
        }
        sharedPublisher.ensureNeverFrozen()
        subscriber.ensureNeverFrozen()

        mapProcessor.subscribe(subscriber)
        assertFalse { subscriber.isFrozen }
    }

    class FreezingDispatchQueue() : TrikotDispatchQueue {
        override fun dispatch(block: DispatchBlock) {
            block.freeze()
            block()
        }
    }
}
