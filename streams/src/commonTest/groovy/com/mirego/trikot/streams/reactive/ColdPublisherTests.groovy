package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import spock.lang.Specification

class ColdPublisherTests extends Specification {

    CancellableManager cancellableManager = new CancellableManager()
    String expectedValue = "a"
    String receivedValue = null
    Boolean executed = false
    FakeCancellable cancellable = new FakeCancellable()

    def executionBlock = { cancellableManager ->
        executed = true
        cancellableManager.add(cancellable)
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
            coldPublisher.subscribe(cancellableManager, {})
        }

        then:
        executed
        !cancellable.isCancelled
    }

    def '''
        given cold publisher
        when unsubscribing
        then cancellable is called
        '''() {
        given:

        when:
        use(PublisherExtensionsKt) {
            coldPublisher.subscribe(cancellableManager, {})
        }
        cancellableManager.cancel()

        then:
        executed
        cancellable.isCancelled
    }

    def '''
        given cold publisher
        when value is set in execution block publisher
        then valus is dispatched
        '''() {
        given:

        when:
        use(PublisherExtensionsKt) {
            coldPublisher.subscribe(cancellableManager, { receivedValue = it })
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
            coldPublisher.subscribe(cancellableManager, { receivedValue = it })
            cancellableManager.cancel()
            executed = false
            coldPublisher.subscribe(new CancellableManager(), { receivedValue = it })
        }

        then:
        executed
    }

    class FakeCancellable implements Cancellable {
        boolean isCancelled = false
        @Override
        void cancel() {
            isCancelled = true
        }
    }
}
