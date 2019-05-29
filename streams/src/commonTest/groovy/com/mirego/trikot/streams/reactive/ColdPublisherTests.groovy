package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancelable.Cancelable
import com.mirego.trikot.streams.cancelable.CancelableManager
import spock.lang.Specification

class ColdPublisherTests extends Specification {

    CancelableManager cancelableManager = new CancelableManager()
    String expectedValue = "a"
    String receivedValue = null
    Boolean executed = false
    FakeCancelable cancelable = new FakeCancelable()

    def executionBlock = { cancelableManager ->
        executed = true
        cancelableManager.add(cancelable)
        return executionPublisher
    }

    SimplePublisher<String> executionPublisher = new SimplePublisher<String>(null)
    ColdPublisher<String> coldPublisher = new ColdPublisher<String>(executionBlock, null)

    def '''
        given cold publisher
        when subscribing
        then block is executed
        '''() {
        given:

        when:
        use(PublisherExtensionsKt) {
            coldPublisher.subscribe(cancelableManager, {})
        }

        then:
        executed
        !cancelable.isCancelled
    }

    def '''
        given cold publisher
        when unsubscribing
        then cancelable is called
        '''() {
        given:

        when:
        use(PublisherExtensionsKt) {
            coldPublisher.subscribe(cancelableManager, {})
        }
        cancelableManager.cancel()

        then:
        executed
        cancelable.isCancelled
    }

    def '''
        given cold publisher
        when value is set in execution block publisher
        then valus is dispatched
        '''() {
        given:

        when:
        use(PublisherExtensionsKt) {
            coldPublisher.subscribe(cancelableManager, { receivedValue = it })
        }
        executionPublisher.value = expectedValue

        then:
        expectedValue == receivedValue
    }

    def '''
        given cold publisher
        when unsubscribing and re subscribing
        then block is reexecuted
        '''() {
        given:

        when:
        use(PublisherExtensionsKt) {
            coldPublisher.subscribe(cancelableManager, { receivedValue = it })
            cancelableManager.cancel()
            executed = false
            coldPublisher.subscribe(new CancelableManager(), { receivedValue = it })
        }

        then:
        executed
    }

    class FakeCancelable implements Cancelable {
        boolean isCancelled = false
        @Override
        void cancel() {
            isCancelled = true
        }
    }
}
