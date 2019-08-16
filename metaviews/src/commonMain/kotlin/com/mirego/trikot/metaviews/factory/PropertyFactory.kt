package com.mirego.trikot.metaviews.factory

import com.mirego.trikot.streams.reactive.BehaviorSubject
import com.mirego.trikot.streams.reactive.Publishers

object PropertyFactory {
    fun <T> create(value: T): BehaviorSubject<T> {
        return Publishers.behaviorSubject(value)
    }
}
