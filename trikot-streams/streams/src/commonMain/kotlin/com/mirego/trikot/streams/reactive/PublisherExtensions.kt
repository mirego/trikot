package com.mirego.trikot.streams.reactive

import ScanWithAccumulatorBlock
import ScanWithSeedProcessor
import ScanWithSeedSupplierBlock
import com.mirego.trikot.foundation.FoundationConfiguration
import com.mirego.trikot.foundation.concurrent.dispatchQueue.TrikotDispatchQueue
import com.mirego.trikot.foundation.timers.TimerFactory
import com.mirego.trikot.streams.StreamsConfiguration
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.backoff.Backoff
import com.mirego.trikot.streams.reactive.backoff.BackoffPolicy
import com.mirego.trikot.streams.reactive.backoff.ExponentialBackoffPolicy
import com.mirego.trikot.streams.reactive.processors.ConcatProcessor
import com.mirego.trikot.streams.reactive.processors.DebounceProcessor
import com.mirego.trikot.streams.reactive.processors.DelayProcessor
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
import com.mirego.trikot.streams.reactive.processors.RejectProcessor
import com.mirego.trikot.streams.reactive.processors.RejectProcessorBlock
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
import com.mirego.trikot.streams.reactive.processors.TakeUntilProcessor
import com.mirego.trikot.streams.reactive.processors.TakeUntilProcessorPredicate
import com.mirego.trikot.streams.reactive.processors.TakeWhileProcessor
import com.mirego.trikot.streams.reactive.processors.TakeWhileProcessorPredicate
import com.mirego.trikot.streams.reactive.processors.ThreadLocalProcessor
import com.mirego.trikot.streams.reactive.processors.TimeoutProcessor
import com.mirego.trikot.streams.reactive.processors.WithCancellableManagerProcessor
import com.mirego.trikot.streams.reactive.processors.WithCancellableManagerProcessorResultType
import com.mirego.trikot.streams.reactive.processors.WithPreviousValueProcessor
import org.reactivestreams.Publisher
import kotlin.time.Duration

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

fun <T> Publisher<T>.observeOn(dispatcher: TrikotDispatchQueue): Publisher<T> {
    return ObserveOnProcessor(this, dispatcher)
}

fun <T> Publisher<T>.subscribeOn(dispatcher: TrikotDispatchQueue): Publisher<T> {
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

fun <T> Publisher<T>.reject(block: RejectProcessorBlock<T>): Publisher<T> {
    return RejectProcessor(this, block)
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
    observeOnQueue: TrikotDispatchQueue,
    subscribeOnQueue: TrikotDispatchQueue = StreamsConfiguration.publisherExecutionDispatchQueue
): Publisher<T> {
    return ThreadLocalProcessor(this, observeOnQueue, subscribeOnQueue)
}

fun <T> Publisher<T>.timeout(
    duration: Duration,
    message: String = "Default timeout message"
): Publisher<T> {
    return TimeoutProcessor(duration = duration, timeoutMessage = message, parentPublisher = this)
}

/**
 * Shift the emissions from the Publisher forward in time by the specified duration
 *
 * The Delay operator modifies its source Publisher by pausing for a particular increment of time
 * (that you specify) before emitting each of the source Publisher’s items. This has the effect of
 * shifting the entire sequence of items emitted by the Publisher forward in time by that
 * specified increment.
 *
 * Marbles diagram :
 * -------(1)---(2)-------(3)--------(4)---|->
 * delay()
 * ----------------(1)---(2)-------(3)-----|->
 *
 * @see @see <a href="http://reactivex.io/documentation/operators/delay.html">http://reactivex.io/documentation/operators/delay.html</a>
 */
fun <T> Publisher<T>.delay(
    duration: Duration,
    timerFactory: TimerFactory = FoundationConfiguration.timerFactory
): Publisher<T> {
    return DelayProcessor(this, duration, timerFactory)
}

fun <T> Publisher<T>.debounce(
    timeout: Duration,
    timerFactory: TimerFactory = FoundationConfiguration.timerFactory
): Publisher<T> {
    return DebounceProcessor(this, timeout, timerFactory)
}

/**
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

/**
 * Returns a {@code Publisher} that emits the provided initial {@code seed} value,
 * then emits a new value by applying the specified accumulator to the previous
 * emitted value and the current value emitted by the upstream publisher.
 *
 * This sort of function is sometimes called an accumulator.
 *
 * Note that the {@code Publisher} that results from this method will emit
 * {@code seed} as its first emitted item.
 *
 * Note that the value returned the {@code seed} is shared among all subscribers
 * to the resulting {@code Publisher} and may cause problems if it is mutable.
 *
 * Note that this is a shortHand for {@code scanWith({ seed }, accumulator)}
 *
 * Marbles diagram :
 * ------------(1)--------(2)----------(3)-------|->
 * scan([]) { acc, x => acc + x }
 * ---([])----([1])----([1, 2])----([1, 2, 3])---|->
 *
 * @see <a href="http://reactivex.io/documentation/operators/scan.html">ReactiveX operators documentation: Scan</a>
 * @see <a href="http://reactivex.io/RxJava/javadoc/rx/Observable.html#collect-rx.functions.Func0-rx.functions.Action2-">http://reactivex.io/RxJava/javadoc/rx/Observable.html#collect-rx.functions.Func0-rx.functions.Action2-</a>
 */
fun <T, R> Publisher<T>.scan(seed: R, accumulator: ScanWithAccumulatorBlock<T, R>) = scanWith({ seed }, accumulator)

/**
 * Returns a {@code Publisher} that emits the provided initial value returned
 * by the {@code seedSupplier}, then emits a new value by applying the specified
 * accumulator to the previous emitted value and the current value emitted by the
 * upstream publisher.
 *
 * This sort of function is sometimes called an accumulator.
 *
 * Note that the {@code Publisher} that results from this method will emit the value
 * returned by the {@code seedSupplier} as its first emitted item.
 *
 * Note that the value returned the {@code seedSupplier} is shared among all subscribers
 * to the resulting {@code Observable} and may cause problems if it is mutable.
 *
 * Marbles diagram :
 * ------------(1)--------(2)--------(3)---------|->
 * scan([]) { acc, x => acc + x }
 * ---([])----([1])----([1, 2])----([1, 2, 3])---|->
 *
 * @see <a href="http://reactivex.io/documentation/operators/scan.html">ReactiveX operators documentation: Scan</a>
 * @see <a href="http://reactivex.io/RxJava/javadoc/rx/Observable.html#collect-rx.functions.Func0-rx.functions.Action2-">http://reactivex.io/RxJava/javadoc/rx/Observable.html#collect-rx.functions.Func0-rx.functions.Action2-</a>
 */
fun <T, R> Publisher<T>.scanWith(
    seedSupplier: ScanWithSeedSupplierBlock<R>,
    accumulator: ScanWithAccumulatorBlock<T, R>
): Publisher<R> {
    return ScanWithSeedProcessor(this, seedSupplier, accumulator)
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

/**
 * Retries all errors with a specific backoff strategy. When backoff strategy returns stop
 * it will emit the last received error.
 */
fun <T> Publisher<T>.retryBackoff(
    backoffPolicy: BackoffPolicy = ExponentialBackoffPolicy(),
    timerFactory: TimerFactory = FoundationConfiguration.timerFactory
) = retryWhen { errors ->
    errors.switchMap {
        when (val backoff = backoffPolicy.nextBackoff()) {
            is Backoff.Stop -> Publishers.error(it)
            is Backoff.Next -> Publishers.timer(backoff.duration, timerFactory)
        }
    }
}

/**
 * The TakeWhile mirrors the source Publisher until a specified condition becomes false,
 * at which point TakeWhile stops mirroring the source Publisher and complete its own Publisher.
 *
 * Marbles diagram :
 * -------(1)---(2)-----(3)-----(4)--|->
 * takeWhile(!=3)
 * -------(1)---(2)------|------------->
 *
 * @see <a href="http://reactivex.io/documentation/operators/takewhile.html">http://reactivex.io/documentation/operators/takewhile.html</a>
 */
fun <T> Publisher<T>.takeWhile(predicate: TakeWhileProcessorPredicate<T>): Publisher<T> {
    return TakeWhileProcessor(this, predicate)
}

/**
 * The TakeUntil uses a predicate function that evaluates the items emitted by the source Publisher
 * to terminate if we mirrors the source OR emit one last item and complete immediately after.
 *
 * Marbles diagram :
 * -------(1)---(2)-----(3)-----(4)--|->
 * takeUntil(==3)
 * -------(1)---(2)-----(3)/----------->
 *
 * @see <a href="http://reactivex.io/RxJava/javadoc/rx/Observable.html#takeUntil-rx.functions.Func1-">http://reactivex.io/RxJava/javadoc/rx/Observable.html#takeUntil-rx.functions.Func1-</a>
 * This is the predicate version, rather than receiving a second Publisher
 */
fun <T> Publisher<T>.takeUntil(predicate: TakeUntilProcessorPredicate<T>): Publisher<T> {
    return TakeUntilProcessor(this, predicate)
}
