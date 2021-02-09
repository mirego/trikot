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
import com.mirego.trikot.streams.reactive.processors.MergeProcessor
import com.mirego.trikot.streams.reactive.processors.ObserveOnProcessor
import com.mirego.trikot.streams.reactive.processors.OnErrorResumeNextBlock
import com.mirego.trikot.streams.reactive.processors.OnErrorResumeNextProcessor
import com.mirego.trikot.streams.reactive.processors.OnErrorReturnProcessor
import com.mirego.trikot.streams.reactive.processors.OnErrorReturnProcessorBlock
import com.mirego.trikot.streams.reactive.processors.RetryWhenProcessor
import com.mirego.trikot.streams.reactive.processors.RetryWhenPublisherBlock
import com.mirego.trikot.streams.reactive.processors.SampleProcessor
import com.mirego.trikot.streams.reactive.processors.ScanProcessor
import com.mirego.trikot.streams.reactive.processors.ScanProcessorBlock
import com.mirego.trikot.streams.reactive.processors.SharedProcessor
import com.mirego.trikot.streams.reactive.processors.SkipProcessor
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

fun <T> Publisher<T>.threadLocal(
    observeOnQueue: DispatchQueue,
    subscribeOnQueue: DispatchQueue = StreamsConfiguration.publisherExecutionDispatchQueue
): Publisher<T> {
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

inline fun <T, reified R : T> Publisher<T>.filterIs() =
    filter { it is R }.map { it as R }

fun Publisher<Boolean>.reverse() = map { !it }

/**
 * The onErrorResumeNext operator intercepts an onError notification from the source Publisher and,
 * instead of passing it through to any publishers, replaces it with some other item or sequence of items,
 * potentially allowing the resulting Publisher to terminate normally or not to terminate at all.
 */
fun <T> Publisher<T>.onErrorResumeNext(block: OnErrorResumeNextBlock<T>): Publisher<T> =
    OnErrorResumeNextProcessor(this, block)

/**
 * Combine multiple Observables into one by merging their emissions
 * Merge may interleave the items emitted by the merged Publishers so order is not guaranteed
 * onError notification from any of the source Publishers will immediately be passed through to subscribers
 * and will terminate the merged Publisher.
 * onComplete notification will only be sent if all merged Publishers are completed
 */
fun <T> Publisher<T>.merge(publishers: List<Publisher<out T>>): Publisher<T> =
    MergeProcessor(this, publishers)

fun <T> Publisher<T>.merge(vararg publishers: Publisher<out T>): Publisher<T> =
    MergeProcessor(this, publishers.toList())

/**
 * Apply a function to each item emitted by a Publisher, sequentially,
 * and emit each successive value
 * This sort of operator is sometimes called an “accumulator” in other contexts.
 */
fun <T> Publisher<T>.scan(block: ScanProcessorBlock<T>): Publisher<T> {
    return ScanProcessor(this, null, block)
}

fun <T> Publisher<T>.scan(initialValue: T, block: ScanProcessorBlock<T>): Publisher<T> {
    return ScanProcessor(this, initialValue, block)
}

/** Suppress the first n items emitted by a Publisher **/
fun <T> Publisher<T>.skip(n: Long) = SkipProcessor(this, n)

/** Periodically looks at publisher and emits whichever item it has most recently emitted since the previous sampling. **/
fun <T> Publisher<T>.sample(
    interval: Duration,
    timerFactory: TimerFactory = FoundationConfiguration.timerFactory
): Publisher<T> {
    return SampleProcessor(this, interval, timerFactory)
}
