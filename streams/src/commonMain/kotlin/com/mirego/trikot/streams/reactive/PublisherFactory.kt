package com.mirego.trikot.streams.reactive

object PublisherFactory {
    fun <T> create(value: T? = null): MutablePublisher<T> {
        return SimplePublisher(value)
    }
}
