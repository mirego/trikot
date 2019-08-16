package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.reactive.PublisherExtensionsKt
import com.mirego.trikot.streams.reactive.SimplePublisher
import org.reactivestreams.Subscriber
import spock.lang.Specification

class FilterProcessorTests extends Specification {
    private subscriberMock = Mock(Subscriber)

    def '''
        given publisher
        when filter return true
        then the signal is dispatch
        '''() {
        given:
        SimplePublisher<Boolean> publisher = new SimplePublisher<String>(null)

        use(PublisherExtensionsKt) { publisher.filter() { return true }.subscribe(subscriberMock) }

        when:
        publisher.value = true

        then:
        1 * subscriberMock.onNext(_)
    }

    def '''
        given publisher
        when filter return false
        then the signal isn't dispatch
        '''() {
        given:
        SimplePublisher<Boolean> publisher = new SimplePublisher<String>(null)

        use(PublisherExtensionsKt) { publisher.filter() { return false }.subscribe(subscriberMock) }

        when:
        publisher.value = true

        then:
        0 * subscriberMock.onNext(_)
    }
}
