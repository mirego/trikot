package com.mirego.trikot.streams.android.ktx

import android.annotation.SuppressLint
import android.os.Looper
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LiveData
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.atomic.AtomicReference

/**
 * This implementation of [LiveDataReactiveStreams] exists so that the publisher
 * values are dispatched to the correct thread. If the request comes from the main thread, we
 * synchronously update the liveData and if we aren't on the main thread we post a new task to the main thread.
 * To do this, we changed the implementation of [PublisherLiveData].onNext()
 *
 *
 * Adapts [LiveData] input and output to the ReactiveStreams spec.
 */
object LiveDataReactiveStreams {
    /**
     * Creates an observable [LiveData] stream from a ReactiveStreams [Publisher]}.
     *
     *
     *
     * When the LiveData becomes active, it subscribes to the emissions from the Publisher.
     *
     *
     *
     * When the LiveData becomes inactive, the subscription is cleared.
     * LiveData holds the last value emitted by the Publisher when the LiveData was active.
     *
     *
     * Therefore, in the case of a hot RxJava Observable, when a new LiveData [Observer] is
     * added, it will automatically notify with the last value held in LiveData,
     * which might not be the last value emitted by the Publisher.
     *
     *
     * Note that LiveData does NOT handle errors and it expects that errors are treated as states
     * in the data that's held. In case of an error being emitted by the publisher, an error will
     * be propagated to the main thread and the app will crash.
     *
     * @param <T> The type of data hold by this instance.
     */
    fun <T> fromPublisher(publisher: Publisher<T>): LiveData<T> {
        return PublisherLiveData(publisher)
    }

    /**
     * Defines a [LiveData] object that wraps a [Publisher].
     *
     *
     *
     * When the LiveData becomes active, it subscribes to the emissions from the Publisher.
     *
     *
     *
     * When the LiveData becomes inactive, the subscription is cleared.
     * LiveData holds the last value emitted by the Publisher when the LiveData was active.
     *
     *
     * Therefore, in the case of a hot RxJava Observable, when a new LiveData [Observer] is
     * added, it will automatically notify with the last value held in LiveData,
     * which might not be the last value emitted by the Publisher.
     *
     *
     *
     * Note that LiveData does NOT handle errors and it expects that errors are treated as states
     * in the data that's held. In case of an error being emitted by the publisher, an error will
     * be propagated to the main thread and the app will crash.
     *
     * @param <T> The type of data hold by this instance.
     */
    private class PublisherLiveData<T> internal constructor(private val mPublisher: Publisher<T>) : LiveData<T>() {
        val mSubscriber: AtomicReference<LiveDataSubscriber> = AtomicReference()

        override fun onActive() {
            super.onActive()

            val liveDataSubscriber = LiveDataSubscriber()
            mSubscriber.set(liveDataSubscriber)
            mPublisher.subscribe(liveDataSubscriber)
        }

        override fun onInactive() {
            super.onInactive()

            val subscriber = mSubscriber.getAndSet(null)
            subscriber?.cancelSubscription()
        }

        internal inner class LiveDataSubscriber : AtomicReference<Subscription?>(), Subscriber<T> {
            override fun onSubscribe(s: Subscription) {
                if (compareAndSet(null, s)) {
                    s.request(Long.MAX_VALUE)
                } else {
                    s.cancel()
                }
            }

            /**
             * Depending on which thread we currently are in, we either update the liveData synchronously
             * or post a new task on the ui thread.
             */
            override fun onNext(t: T) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    setValue(t)
                } else {
                    postValue(t)
                }
            }

            @SuppressLint("RestrictedApi")
            override fun onError(t: Throwable) {
                mSubscriber.compareAndSet(this, null)
                ArchTaskExecutor.getInstance().executeOnMainThread {
                    // Errors should be handled upstream, so propagate as a crash.
                    throw RuntimeException(
                        "LiveData does not handle errors. Errors from "
                            + "publishers should be handled upstream and propagated as "
                            + "state", t
                    )
                }
            }

            override fun onComplete() {
                mSubscriber.compareAndSet(this, null)
            }

            fun cancelSubscription() {
                get()?.cancel()
            }
        }
    }
}
