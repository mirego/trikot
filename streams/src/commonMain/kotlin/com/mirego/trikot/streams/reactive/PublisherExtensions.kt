package com.mirego.trikot.streams.reactive

import com.mirego.trikot.foundation.FoundationConfiguration
import com.mirego.trikot.foundation.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.foundation.timers.TimerFactory
import com.mirego.trikot.streams.StreamsConfiguration
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.processors.ConcatProcessor
import com.mirego.trikot.streams.reactive.processors.DebounceProcessor
import com.mirego.trikot.streams.reactive.processors.DistinctUntilChangedProcessor
import com.mirego.trikot.streams.reactive.processors.FilterProcessor
import com.mirego.trikot.streams.reactive.processors.FilterProcessorBlock
import com.mirego.trikot.streams.reactive.processors.FirstProcessor
import com.mirego.trikot.streams.reactive.processors.MapProcessor
import com.mirego.trikot.streams.reactive.processors.MapProcessorBlock
import com.mirego.trikot.streams.reactive.processors.ObserveOnProcessor
import com.mirego.trikot.streams.reactive.processors.OnErrorReturnProcessor
import com.mirego.trikot.streams.reactive.processors.OnErrorReturnProcessorBlock
import com.mirego.trikot.streams.reactive.processors.RetryWhenProcessor
import com.mirego.trikot.streams.reactive.processors.RetryWhenPublisherBlock
import com.mirego.trikot.streams.reactive.processors.SharedProcessor
import com.mirego.trikot.streams.reactive.processors.SubscribeOnProcessor
import com.mirego.trikot.streams.reactive.processors.SwitchMapProcessor
import com.mirego.trikot.streams.reactive.processors.SwitchMapProcessorBlock
import com.mirego.trikot.streams.reactive.processors.ThreadLocalProcessor
import com.mirego.trikot.streams.reactive.processors.TimeoutProcessor
import com.mirego.trikot.streams.reactive.processors.WithCancellableManagerProcessor
import com.mirego.trikot.streams.reactive.processors.WithCancellableManagerProcessorResultType
import com.mirego.trikot.streams.reactive.processors.WithPreviousValueProcessor
import org.reactivestreams.Publisher
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

typealias SubscriptionBlock<T> = (T) -> Unit
typealias SubscriptionErrorBlock = (Throwable) -> Unit
typealias SubscriptionCompletedBlock = () -> Unit

fun <T> Publisher<T>.subscribe(
    cancellableManager: CancellableManager,
    onNext: SubscriptionBlock<T>
) {
    subscribe(cancellableManager, onNext, null, null)
}

fun <T> Publisher<T>.subscribe(
    cancellableManager: CancellableManager,
    onNext: SubscriptionBlock<T>,
    onError: SubscriptionErrorBlock?
) {
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

fun <T> Publisher<T>.concat(publisher: Publisher<T>): Publisher<T> {
    return ConcatProcessor(this, publisher)
}

fun <T> Publisher<T>.startWith(value: T): Publisher<T> {
    return ConcatProcessor(value.just(), this)
}

fun <T, R> Publisher<T>.filterNotNull(block: ((T) -> R?)): Publisher<R> {
    return this.filter { block(it) != null }.map { block(it)!! }
}

fun <T> Publisher<T>.threadLocal(observeOnQueue: DispatchQueue, subscribeOnQueue: DispatchQueue = StreamsConfiguration.publisherExecutionDispatchQueue): Publisher<T> {
    return ThreadLocalProcessor(this, observeOnQueue, subscribeOnQueue)
}

fun <T> Publisher<T>.timeout(
    duration: Duration,
    message: String = "Default timeout message"
): Publisher<T> {
    return TimeoutProcessor(duration = duration, timeoutMessage = message, parentPublisher = this)
}

@ExperimentalTime
fun <T> Publisher<T>.debounce(
    timeout: Duration,
    timerFactory: TimerFactory = FoundationConfiguration.timerFactory
): Publisher<T> {
    return DebounceProcessor(this, timeout, timerFactory)
}

/**+
 * Returns a Publisher that mirrors the source Publisher with the exception of an error.
 * If the source Publisher calls error, this method will emit the Throwable that caused the error to the Publisher returned from notifier.
 * If that Publisher calls complete or error then this method will call complete or error on the child subscription.
 * Otherwise this method will resubscribe to the source Publisher.
 */
fun <T> Publisher<T>.retryWhen(block: RetryWhenPublisherBlock): Publisher<T> {
    return RetryWhenProcessor(this, block)
}
