package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancelable.CancelableManager
import org.reactivestreams.Publisher
import spock.lang.Specification

class CombineLatestTests extends Specification {
    SimplePublisher<String> stringPublisher = new SimplePublisher(null)
    SimplePublisher<Integer> intPublisher = new SimplePublisher(null)
    Publisher combineLatest = CombineLatest.@Companion.combine2(stringPublisher, intPublisher)
    CancelableManager cancelableManager = new CancelableManager()
    CombineLatestResult2 result = null

    def '''
        given CombineLatest with 2 publishers
        when subscribing and one publisher has not dispatched value yet
        then value is not dispatched to subscribers
        '''() {
        given:
        stringPublisher.value = "a"

        when:
        use(PublisherExtensionsKt) {
            combineLatest.subscribe(cancelableManager) { result = it }
        }

        then:
        result == null
    }

    def '''
        given CombineLatest with 2 publishers that has values
        when subscribing
        then value is dispatched to subscribers
        '''() {
        given:
        stringPublisher.value = "a"
        intPublisher.value = 1

        when:
        use(PublisherExtensionsKt) {
            combineLatest.subscribe(cancelableManager) { result = it }
        }

        then:
        result.component1 == "a"
        result.component2 == 1
    }

    def '''
        given CombineLatest with 2 publishers and a subscriber
        when canceling the subscription and the value is updated
        then value is not dispatched
        '''() {
        given:
        stringPublisher.value = "a"
        intPublisher.value = 1
        use(PublisherExtensionsKt) {
            combineLatest.subscribe(cancelableManager) { result = it }
        }

        when:
        cancelableManager.cancel()
        stringPublisher.value = "b"

        then:
        result.component1 == "a"
        result.component2 == 1
    }

    def '''
        given CombineLatest with 2 publishers with value and a subscriber
        when value is updated
        then value is dispatched
        '''() {
        given:
        stringPublisher.value = "a"
        intPublisher.value = 1
        use(PublisherExtensionsKt) {
            combineLatest.subscribe(cancelableManager) { result = it }
        }

        when:
        stringPublisher.value = "b"

        then:
        result.component1 == "b"
        result.component2 == 1
    }

}
