package com.mirego.trikot.streams.reactive

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

fun <T> Publisher<T>.asConcretePublisher(): ConcretePublisher<T> = ConcretePublisher(this)

class ConcretePublisher<T>(private val publisher: Publisher<T>) : Publisher<T> {
    override fun subscribe(s: Subscriber<in T>) {
        publisher.subscribe(s)
    }
}
