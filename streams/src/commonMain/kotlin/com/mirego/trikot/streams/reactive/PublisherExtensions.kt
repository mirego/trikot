package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.foundation.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.streams.reactive.processors.MapProcessor
import com.mirego.trikot.streams.reactive.processors.MapProcessorBlock
import com.mirego.trikot.streams.reactive.processors.SwitchMapProcessor
import com.mirego.trikot.streams.reactive.processors.SwitchMapProcessorBlock
import com.mirego.trikot.streams.reactive.processors.ObserveOnProcessor
import com.mirego.trikot.streams.reactive.processors.SubscribeOnProcessor
import com.mirego.trikot.streams.reactive.processors.FirstProcessor
import com.mirego.trikot.streams.reactive.processors.WithCancellableManagerProcessor
import com.mirego.trikot.streams.reactive.processors.WithCancellableManagerProcessorResultType
import com.mirego.trikot.streams.reactive.processors.FilterProcessorBlock
import com.mirego.trikot.streams.reactive.processors.FilterProcessor
import com.mirego.trikot.streams.reactive.processors.SharedProcessor
import com.mirego.trikot.streams.reactive.processors.OnErrorReturnProcessor
import com.mirego.trikot.streams.reactive.processors.OnErrorReturnProcessorBlock
import com.mirego.trikot.streams.reactive.processors.DistinctUntilChangedProcessor
import com.mirego.trikot.streams.reactive.processors.WithPreviousValueProcessor
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

typealias SubscriptionBlock<T> = (T) -> Unit
typealias SubscriptionErrorBlock = (Throwable) -> Unit
typealias SubscriptionCompletedBlock = () -> Unit

fun <T> Publisher<T>.subscribe(cancellableManager: CancellableManager, onNext: SubscriptionBlock<T>) {
    subscribe(cancellableManager, onNext, null, null)
}

fun <T> Publisher<T>.subscribe(cancellableManager: CancellableManager, onNext: SubscriptionBlock<T>, onError: SubscriptionErrorBlock?) {
    subscribe(cancellableManager, onNext, onError, null)
}

fun <T> Publisher<T>.subscribe(
    cancellableManager: CancellableManager,
    onNext: SubscriptionBlock<T>,
    onError: SubscriptionErrorBlock?,
    onCompleted: SubscriptionCompletedBlock?
) {
    subscribe(SubscriberFromBlock(cancellableManager, onNext, onError, onCompleted))
}

fun <T, R> Publisher<T>.map(block: MapProcessorBlock<T, R>): Publisher<R> {
    return MapProcessor(this, block)
}

fun <T, R> Publisher<T>.switchMap(block: SwitchMapProcessorBlock<T, R>): Publisher<R> {
    return SwitchMapProcessor(this, block)
}

fun <T> Publisher<T>.observeOn(dispatcher: DispatchQueue): Publisher<T> {
    return ObserveOnProcessor(this, dispatcher)
}

fun <T> Publisher<T>.subscribeOn(dispatcher: DispatchQueue): Publisher<T> {
    return SubscribeOnProcessor(this, dispatcher)
}

fun <T> Publisher<T>.first(): Publisher<T> {
    return FirstProcessor(this)
}

fun <T> Publisher<T>.withCancellableManager(): Publisher<WithCancellableManagerProcessorResultType<T>> {
    return WithCancellableManagerProcessor(this)
}

fun <T> Publisher<T>.filter(block: FilterProcessorBlock<T>): Publisher<T> {
    return FilterProcessor(this, block)
}

fun <T> Publisher<T>.shared(): Publisher<T> {
    return SharedProcessor(this)
}

fun <T> Publisher<T>.onErrorReturn(block: OnErrorReturnProcessorBlock<T>): Publisher<T> {
    return OnErrorReturnProcessor(this, block)
}

fun <T> Publisher<T>.distinctUntilChanged(): Publisher<T> {
    return DistinctUntilChangedProcessor(this)
}

fun <T> Publisher<T>.withPreviousValue(): Publisher<Pair<T?, T>> {
    return WithPreviousValueProcessor(this)
}

fun <T> Publisher<T>.asMutable(): BehaviorSubject<T> {
    val referencePublisher = this
    return object : BehaviorSubject<T> {
        override var value: T?
            get() {
                throw IllegalArgumentException("Cannot use get value on publisher of type on ${referencePublisher::class}.")
            }
            set(_) {
                throw IllegalArgumentException("Cannot use set value on publisher of type on ${referencePublisher::class}.")
            }
        override var error: Throwable?
            get() {
                throw IllegalArgumentException("Cannot use get error on publisher of type on ${referencePublisher::class}.")
            }
            set(_) {
                throw IllegalArgumentException("Cannot use set error on publisher of type on ${referencePublisher::class}.")
            }

        override fun complete() {
            throw IllegalArgumentException("Cannot use complete on publisher of type on ${referencePublisher::class}.")
        }

        override fun subscribe(s: Subscriber<in T>) {
            referencePublisher.subscribe(s)
        }
    }
}

fun <T> T.asPublisher(): Publisher<T> {
    return Publishers.behaviorSubject(this)
}
