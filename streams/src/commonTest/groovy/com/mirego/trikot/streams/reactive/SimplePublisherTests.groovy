package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.CancellableManager
import org.jetbrains.annotations.Nullable
import spock.lang.Specification

class SimplePublisherTests extends Specification {
    CancellableManager cancellableManager = new CancellableManager()
    String expectedValue = "a"
    Exception expectedError = new Exception("expected exception")

    def '''
        given publisher with a value
        when subscribing
        then value is dispatched
        '''() {
        given:
        SimplePublisher publisher = createPublisher()
        publisher.value = expectedValue
        String receivedValue = null

        when:
        use(PublisherExtensionsKt) {
            publisher.subscribe(cancellableManager, { receivedValue = it })
        }

        then:
        receivedValue == expectedValue
    }

    def '''
        given publisher with an error
        when subscribing
        then error is dispatched
        '''() {
        given:
        SimplePublisher publisher = createPublisher()
        publisher.error = expectedError
        Exception receivedError = null

        when:
        use(PublisherExtensionsKt) {
            publisher.subscribe(cancellableManager, {}, { receivedError = it })
        }

        then:
        receivedError == expectedError
    }

    def '''
        given a subscriber attached to a publisher with a value
        when the value changed
        then the new value is dispatched
        '''() {
        given:
        SimplePublisher publisher = createPublisher()
        publisher.value = "first value"
        String receivedValue = null
        use(PublisherExtensionsKt) {
            publisher.subscribe(cancellableManager, { receivedValue = it })
        }

        when:
        publisher.value = expectedValue

        then:
        receivedValue == expectedValue
    }


    def '''
        given publisher with a cancelled subscriber
        when publishing a new value
        then value is not dispatched
        '''() {
        given:
        SimplePublisher publisher = createPublisher()
        publisher.value = expectedValue
        String receivedValue = null
        use(PublisherExtensionsKt) {
            publisher.subscribe(cancellableManager, { receivedValue = it })
        }

        when:
        cancellableManager.cancel()
        publisher.value = "you shall not pass"

        then:
        receivedValue == expectedValue
    }

    def '''
        given publisher with a value
        when subscribing with a cancelled manager
        then value is not dispatched
        '''() {
        given:
        SimplePublisher publisher = createPublisher()
        publisher.value = expectedValue
        String receivedValue = null

        when:
        cancellableManager.cancel()
        use(PublisherExtensionsKt) {
            publisher.subscribe(cancellableManager, { receivedValue = it })
        }

        then:
        receivedValue == null
    }

    def '''
        given simple publisher
        when subscribing
        then firstSubscription is called
        '''() {
        given:
        SimplePublisherImplementation publisher = new SimplePublisherImplementation("")

        when:
        use(PublisherExtensionsKt) {
            publisher.subscribe(cancellableManager, {})
        }

        then:
        publisher.firstSubscriptionCalled
    }

    def '''
        given simple publisher
        when last subscriber is unsubscribed
        then onNoSubscriber is called
        '''() {
        given:
        SimplePublisherImplementation publisher = new SimplePublisherImplementation("")
        use(PublisherExtensionsKt) {
            publisher.subscribe(cancellableManager, {})
        }

        when:
        cancellableManager.cancel()

        then:
        publisher.noSubscriptionCalled
    }

    def '''
        given simple publisher
        when marking as completed
        then onComplete is called
        '''() {
        given:
        SimplePublisher publisher = createPublisher()
        Boolean isCompleted = false
        use(PublisherExtensionsKt) {
            publisher.subscribe(cancellableManager, {}, {}, { isCompleted = true } )
        }

        when:
        publisher.complete()

        then:
        isCompleted
    }

    def '''
        given completed publisher
        when setting a new value
        then exception is thrown
        '''() {
        given:
        SimplePublisher publisher = createPublisher()
        publisher.complete()

        when:
        publisher.value = "a"

        then:
        thrown IllegalStateException
    }


    def '''
        given simple publisher
        when subscribing with a cancelled subsciption manager
        then firstSubscription is not called
        '''() {
        given:
        SimplePublisherImplementation publisher = new SimplePublisherImplementation("")
        cancellableManager.cancel()

        when:
        use(PublisherExtensionsKt) {
            publisher.subscribe(cancellableManager, {})
        }

        then:
        !publisher.firstSubscriptionCalled
    }

    class SimplePublisherImplementation extends SimplePublisher<String> {
        Boolean firstSubscriptionCalled = false
        Boolean noSubscriptionCalled = false

        SimplePublisherImplementation(@Nullable String value) {
            super(value)
        }

        @Override
        protected void onFirstSubscription() {
            super.onFirstSubscription()
            firstSubscriptionCalled = true
        }

        @Override
        protected void onNoSubscription() {
            super.onNoSubscription()
            noSubscriptionCalled = true
        }
    }

    SimplePublisher<String> createPublisher() {
        return new SimplePublisher<String>(null)
    }
}
