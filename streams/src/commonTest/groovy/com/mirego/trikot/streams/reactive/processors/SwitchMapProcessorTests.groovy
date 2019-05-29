package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.concurrent.dispatchQueue.SynchronousDispatchQueue
import com.mirego.trikot.streams.reactive.PublisherExtensionsKt
import com.mirego.trikot.streams.reactive.SimplePublisher
import org.reactivestreams.Subscriber
import spock.lang.Specification

class SwitchMapProcessorTests extends Specification {
    private subscriberMock = Mock(Subscriber)

    def '''
        given publisher
        when ...
        then ...
        '''() {
        given:
        SimplePublisher<Boolean> publisher = new SimplePublisher<String>(null)
        SimplePublisher<Boolean> returnedPublisher = new SimplePublisher<String>(null)
        SimplePublisher<Boolean> returnedPublisher2 = new SimplePublisher<String>(null)

        use(PublisherExtensionsKt) { publisher.switchMap() { return returnedPublisher }.switchMap() { return returnedPublisher2 }.subscribe(subscriberMock) }

        when:
        publisher.value = "A"
        returnedPublisher.value = "B"
        returnedPublisher2.value = "C"

        then:
        1 * subscriberMock.onNext("C")
    }
}
