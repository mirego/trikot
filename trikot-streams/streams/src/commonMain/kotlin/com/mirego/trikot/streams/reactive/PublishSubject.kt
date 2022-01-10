package com.mirego.trikot.streams.reactive

interface PublishSubject<T> : MutablePublisher<T> {
    override var value: T?
    override var error: Throwable?
    override fun complete()
}
