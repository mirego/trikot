package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.PublisherExtensionsKt
import com.mirego.trikot.streams.reactive.SimplePublisher
import org.jetbrains.annotations.NotNull
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import spock.lang.Specification

class AbstractProcessorTests extends Specification {
    Publisher<String> publisherMock = Mock(Publisher)
    TestProcessor processor = new TestProcessor(publisherMock)
    CancellableManager cancellableManager = new CancellableManager()

    String input = "input"
    String output = input.toUpperCase()

    def '''
        given processor
        when subscribed to
        then processor is subscribed to parent
        '''() {
        when:
        use(PublisherExtensionsKt) {
            processor.subscribe(cancellableManager) {}
        }

        then:
        1 * publisherMock.subscribe(_)
    }

    def '''
        given a subscribed processor
        when subscription is cancelled
        then parent subscription is cancelled
        '''() {
        given:
        Subscription subscription = Mock(Subscription)

        when:
        use(PublisherExtensionsKt) {
            processor.subscribe(cancellableManager) {}
        }
        processor.onSubscribe(subscription)
        cancellableManager.cancel()

        then:
        1 * publisherMock.subscribe(_)
    }

    def '''
        given a subscibed processor
        when parent publisher dispatch value
        then processor receive the value
        '''() {
        given:
        SimplePublisher<String> publisher = new SimplePublisher<String>("")
        TestProcessor processor = new TestProcessor(publisher)
        String receivedValue = null

        when:
        publisher.value = input
        use(PublisherExtensionsKt) {
            processor.subscribe(cancellableManager) { receivedValue = it }
        }

        then:
        receivedValue == output
    }

    class TestProcessor extends AbstractProcessor<String, String> {

        TestProcessor(@NotNull Publisher<String> parentPublisher) {
            super(parentPublisher)
        }

        @Override
        ProcessorSubscription<String, String> createSubscription(@NotNull Subscriber<? super String> subscriber) {
            return new TestProcessorSubscription(subscriber)
        }

        class TestProcessorSubscription extends ProcessorSubscription {
            boolean isCancelled = false

            TestProcessorSubscription(@NotNull Subscriber subscriber) {
                super(subscriber)
            }

            @Override
            void onCancel(Subscription s) {
                super.onCancel()
                isCancelled = true
            }

            @Override
            void onNext(Object o, @NotNull Subscriber subscriber) {
                subscriber.onNext(((String) o).toUpperCase())
            }
        }
    }
}
