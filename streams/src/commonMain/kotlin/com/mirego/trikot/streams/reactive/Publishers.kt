package com.mirego.trikot.streams.reactive

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

object Publishers {
    fun <T> behaviorSubject(value: T? = null): BehaviorSubject<T> {
        return BehaviorSubjectImpl(value)
    }

    fun <T> publishSubject(): PublishSubject<T> {
        return PublishSubjectImpl()
    }

    /**
     * Create a Publisher that emits a particular item
     * @see <a href="http://reactivex.io/documentation/operators/just.html">http://reactivex.io/documentation/operators/just.html</a>
     */
    fun <T> just(value: T): Publisher<T> {
        return JustPublisher(listOf(value))
    }

    fun <T> justMany(vararg values: T): Publisher<T> {
        return JustPublisher(values.toList())
    }

    /**
     * Create a Publisher that emits the provided iterable sequence of items
     * @see <a href="http://reactivex.io/documentation/operators/from.html">http://reactivex.io/documentation/operators/from.html</a>
     */
    fun <T> fromIterable(iterable: Iterable<T>): Publisher<T> {
        return JustPublisher(iterable)
    }

    /**
     * Create a Publisher that emits no items but terminates normally
     * @see <a href="http://reactivex.io/documentation/operators/empty-never-throw.html">http://reactivex.io/documentation/operators/empty-never-throw.html</a>
     */
    fun <T> empty(): Publisher<T> {
        return publishSubject<T>().also { it.complete() }
    }

    /**
     * Create a Publisher that emits no items and does not terminate
     * @see <a href="http://reactivex.io/documentation/operators/empty-never-throw.html">http://reactivex.io/documentation/operators/empty-never-throw.html</a>
     */
    fun <T> never(): Publisher<T> {
        return object : Publisher<T> {
            override fun subscribe(s: Subscriber<in T>) {
                s.onSubscribe(
                    object : Subscription {
                        override fun request(n: Long) {
                        }

                        override fun cancel() {
                        }
                    }
                )
            }
        }
    }

    /**
     * Create a Publisher that emits no items and terminates with an error
     * @see <a href="http://reactivex.io/documentation/operators/empty-never-throw.html">http://reactivex.io/documentation/operators/empty-never-throw.html</a>
     */
    fun <T> error(throwable: Throwable): Publisher<T> {
        return behaviorSubject<T>().also { it.error = throwable }
    }

    @Deprecated("Use empty() instead", ReplaceWith("Publishers.empty<T>()"))
    fun <T> completed(): Publisher<T> {
        return publishSubject<T>().also { it.complete() }
    }
}

fun <T> T.asPublisher(): Publisher<T> = Publishers.behaviorSubject(this)

fun <T> T.just(): Publisher<T> = Publishers.just(this)
