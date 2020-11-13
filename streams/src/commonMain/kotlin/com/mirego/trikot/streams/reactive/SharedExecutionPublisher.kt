package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousSerialQueue
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class SharedExecutionPublisher<T>(private val block: () -> Publisher<T>) : Publisher<T> {
    private val currentPublisher = AtomicReference(block())
    private val refCount = AtomicReference(0)
    private val serialQueue = SynchronousSerialQueue()

    override fun subscribe(s: Subscriber<in T>) {
        currentPublisher.value.subscribe(object : Subscriber<T> {
            private val subscriber = s

            override fun onComplete() {
                subscriber.onComplete()
                onFinish()
            }

            override fun onError(t: Throwable) {
                subscriber.onError(t)
                onFinish()
            }

            override fun onNext(t: T) {
                subscriber.onNext(t)
            }

            override fun onSubscribe(s: Subscription) {
                serialQueue.dispatch {
                    refCount.setOrThrow(refCount.value, refCount.value + 1)
                }
                subscriber.onSubscribe(object : Subscription {
                    override fun cancel() {
                        s.cancel()
                        onCancel()
                    }

                    override fun request(n: Long) {
                        s.request(n)
                    }
                })
            }
        })
    }

    private fun onCancel() {
        serialQueue.dispatch {
            if (refCount.value > 0) {
                refCount.setOrThrow(refCount.value, refCount.value - 1)
                if (refCount.value == 0) {
                    currentPublisher.setOrThrow(currentPublisher.value, block())
                }
            }
        }
    }

    private fun onFinish() {
        serialQueue.dispatch {
            if (refCount.value > 0) {
                refCount.setOrThrow(refCount.value, 0)
                currentPublisher.setOrThrow(currentPublisher.value, block())
            }
        }
    }
}
