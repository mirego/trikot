package com.mirego.trikot.viewmodels.declarative.utilities

import com.mirego.trikot.streams.reactive.MutablePublisher

val <T> MutablePublisher<T>.valueOrThrow: T
    get() {
        return value!!
    }
