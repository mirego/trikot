package com.mirego.trikot.streams.cancellable

import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import spock.lang.Specification

class CancellableManagerTests extends Specification {

    def '''
        given cancellableManager with a cancellable
        when cancelled
        then cancel is called
        '''() {
        given:
        CancellableManager cancellableManager = new CancellableManager()
        Cancellable cancellable = Mock(Cancellable)
        cancellableManager.add(cancellable)

        when:
        cancellableManager.cancel()

        then:
        1 * cancellable.cancel()
    }

    def '''
        given cancelled cancellableManager
        when adding a cancellable
        then cancel is called
        '''() {
        given:
        CancellableManager cancellableManager = new CancellableManager()
        cancellableManager.cancel()
        Cancellable cancellable = Mock(Cancellable)

        when:
        cancellableManager.add(cancellable)

        then:
        1 * cancellable.cancel()
    }
}
