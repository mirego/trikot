package com.mirego.trikot.streams.reactive

import org.reactivestreams.Publisher

object Publishers {
    fun <T> behaviorSubject(value: T? = null): BehaviorSubject<T> {
        return BehaviorSubjectImpl(value)
    }

    fun <T> publishSubject(): PublishSubject<T> {
        return PublishSubjectImpl()
    }

    fun <T> just(value: T): Publisher<T> {
        return behaviorSubject(value).also { it.complete() }
    }

    fun <T> empty(): Publisher<T> {
        return PublishSubjectImpl()
    }

    fun <T> completed(): Publisher<T> {
        return PublishSubjectImpl<T>().also { it.complete() }
    }
}
