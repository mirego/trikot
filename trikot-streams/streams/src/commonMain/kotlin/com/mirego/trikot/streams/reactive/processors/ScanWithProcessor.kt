import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import com.mirego.trikot.streams.reactive.processors.AbstractProcessor
import com.mirego.trikot.streams.reactive.processors.ProcessorSubscription
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

typealias ScanWithSeedSupplierBlock<T> = () -> T
typealias ScanWithAccumulatorBlock<T, R> = (acc: R, current: T) -> R

class ScanWithSeedProcessor<T, R>(
    parentPublisher: Publisher<T>,
    private val seedSupplier: ScanWithSeedSupplierBlock<R>,
    private val accumulator: ScanWithAccumulatorBlock<T, R>
) : AbstractProcessor<T, R>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in R>): ProcessorSubscription<T, R> {
        return ScanProcessorSubscription(subscriber, seedSupplier.invoke(), accumulator)
    }

    private class ScanProcessorSubscription<T, R>(
        private val subscriber: Subscriber<in R>,
        seed: R,
        private val accumulator: ScanWithAccumulatorBlock<T, R>
    ) : ProcessorSubscription<T, R>(subscriber) {

        private var value: R by atomic(seed)

        override fun onSubscribe(s: Subscription) {
            subscriber.onNext(value)
            super.onSubscribe(s)
        }

        override fun onNext(t: T, subscriber: Subscriber<in R>) {
            val nextValue = try {
                accumulator.invoke(value, t)
            } catch (e: StreamsProcessorException) {
                subscriber.onError(e)
                return
            }
            value = nextValue
            subscriber.onNext(nextValue)
        }
    }
}
