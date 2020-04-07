package com.mirego.trikot.streams.reactive

import org.reactivestreams.Publisher
import kotlin.js.JsName

object Publishers {
    @JsName("behaviorSubject")
    fun <T> behaviorSubject(value: T? = null): BehaviorSubject<T> {
        return BehaviorSubjectImpl(value)
    }

    @JsName("publishSubject")
    fun <T> publishSubject(): PublishSubject<T> {
        return PublishSubjectImpl()
    }

    @JsName("just")
    fun <T> just(value: T): Publisher<T> {
        return behaviorSubject(value).also { it.complete() }
    }

    @JsName("empty")
    fun <T> empty(): Publisher<T> {
        return PublishSubjectImpl()
    }

    @JsName("completed")
    fun <T> completed(): Publisher<T> {
        return PublishSubjectImpl<T>().also { it.complete() }
    }

    @JsName("error")
    fun <T> error(error: Throwable): Publisher<T> {
        return behaviorSubject<T>().also { it.error = error }
    }
}

fun <T> T.asPublisher(): Publisher<T> = Publishers.behaviorSubject(this)

fun <T> T.just(): Publisher<T> = Publishers.just(this)
