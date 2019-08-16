package com.mirego.trikot.streams.reactive

object Publishers {
    fun <T> behaviorSubject(value: T? = null): BehaviorSubject<T> {
        return BehaviorSubjectImpl(value)
    }
}
