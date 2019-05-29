package com.mirego.trikot.streams.reactive.connectable

import com.mirego.trikot.streams.cancelable.Cancelable
import com.mirego.trikot.streams.reactive.SimplePublisher
import spock.lang.Specification

class ConnectablePublisherTests extends Specification {
    String expectedValue = "a"
    String receivedValue = null
    Boolean executed = false

    SimplePublisher<String> internalPublisher = new SimplePublisher<String>(null)

    def executionBlock = { value ->
        receivedValue = value
        executed = true
        return internalPublisher
    }

    ConnectablePublisher<String> connectablePublisher = new ConnectablePublisher<String>(executionBlock, null)

    def '''
        given connectable publisher
        when connecting
        then publisher is connected
        '''() {
        when:
        connectablePublisher.connect()
        internalPublisher.value = expectedValue

        then:
        executed
        connectablePublisher.value == expectedValue
    }

    def '''
        given connected publisher
        when disconnecting
        then publisher is unsubscribed
        '''() {
        given:
        Cancelable cancelable = connectablePublisher.connect()

        when:
        internalPublisher.value = expectedValue
        cancelable.cancel()
        internalPublisher.value = "Value not received"

        then:
        connectablePublisher.value == expectedValue
    }

    def '''
        given connected connectable
        when disconnecting and reconnecting
        then block is reexecuted
        '''() {
        given:
        Cancelable cancelable = connectablePublisher.connect()

        when:
        cancelable.cancel()
        executed = false
        connectablePublisher.connect()

        then:
        executed
    }

    def '''
        given connectablePublisher
        when connectingTwice
        then exception is thrown
        '''() {
        when:
        connectablePublisher.connect()
        connectablePublisher.connect()

        then:
        thrown AlreadyConnectedException
    }
}
