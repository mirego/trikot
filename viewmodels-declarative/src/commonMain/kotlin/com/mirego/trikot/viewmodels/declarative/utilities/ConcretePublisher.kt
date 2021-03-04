package com.mirego.trikot.viewmodels.declarative.utilities

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

fun <T : Any> Publisher<T>.asConcretePublisher(): ConcretePublisher<T> = ConcretePublisher(this)

class ConcretePublisher<T : Any>(private val publisher: Publisher<T>) : Publisher<T> {
    override fun subscribe(s: Subscriber<in T>) {
        publisher.subscribe(s)
    }
}
