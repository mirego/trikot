import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.streams.reactive.StreamsProcessorException
import com.mirego.trikot.streams.reactive.processors.AbstractProcessor
import com.mirego.trikot.streams.reactive.processors.ProcessorSubscription
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

typealias CollectorBlock<T, R> = (R, T) -> R

class CollectProcessor<T, R>(
    parentPublisher: Publisher<T>,
    private val seed: R,
    private val collector: CollectorBlock<T, R>
) : AbstractProcessor<T, R>(parentPublisher) {

    override fun createSubscription(subscriber: Subscriber<in R>): ProcessorSubscription<T, R> {
        return CollectProcessorSubscription(subscriber, seed, collector)
    }

    private class CollectProcessorSubscription<T, R>(
        subscriber: Subscriber<in R>,
        seed: R,
        private val collector: CollectorBlock<T, R>
    ) : ProcessorSubscription<T, R>(subscriber) {

        private var value: R by atomic(seed)

        override fun onNext(t: T, subscriber: Subscriber<in R>) {
            val nextValue = try {
                collector.invoke(value, t)
            } catch (e: StreamsProcessorException) {
                subscriber.onError(e)
                return
            }
            value = nextValue
            subscriber.onNext(nextValue)
        }
    }
}
