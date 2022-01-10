package com.mirego.trikot.streams.reactive

interface BehaviorSubject<T> : MutablePublisher<T> {
    override var value: T?
    override var error: Throwable?
    override fun complete()
}
