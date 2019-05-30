package com.mirego.trikot.metaviews.factory

import com.mirego.trikot.streams.reactive.MutablePublisher
import com.mirego.trikot.streams.reactive.PublisherFactory

object PropertyFactory {
    fun <T> create(value: T): MutablePublisher<T> {
        return PublisherFactory.create(value)
    }
}
