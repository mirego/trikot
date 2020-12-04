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

    /**
     * Create a Publisher that emits a particular item
     * @see <a href="http://reactivex.io/documentation/operators/just.html">http://reactivex.io/documentation/operators/just.html</a>
     */
    @JsName("just")
    fun <T> just(value: T): Publisher<T> {
        return JustPublisher(value)
    }

    /**
     * Create a Publisher that emits no items but terminates normally
     * @see <a href="http://reactivex.io/documentation/operators/empty-never-throw.html">http://reactivex.io/documentation/operators/empty-never-throw.html</a>
     */
    @JsName("empty")
    fun <T> empty(): Publisher<T> {
        return publishSubject<T>().also { it.complete() }
    }

    /**
     * Create a Publisher that emits no items and does not terminate
     * @see <a href="http://reactivex.io/documentation/operators/empty-never-throw.html">http://reactivex.io/documentation/operators/empty-never-throw.html</a>
     */
    @JsName("never")
    fun <T> never(): Publisher<T> {
        return publishSubject()
    }

    /**
     * Create a Publisher that emits no items and terminates with an error
     * @see <a href="http://reactivex.io/documentation/operators/empty-never-throw.html">http://reactivex.io/documentation/operators/empty-never-throw.html</a>
     */
    @JsName("error")
    fun <T> error(throwable: Throwable): Publisher<T> {
        return behaviorSubject<T>().also { it.error = throwable }
    }

    @Deprecated("Use empty() instead", ReplaceWith("Publishers.empty<T>()"))
    @JsName("completed")
    fun <T> completed(): Publisher<T> {
        return publishSubject<T>().also { it.complete() }
    }
}

fun <T> T.asPublisher(): Publisher<T> = Publishers.behaviorSubject(this)

fun <T> T.just(): Publisher<T> = Publishers.just(this)
