package com.mirego.trikot.streams.cancelable

import com.mirego.trikot.streams.cancelable.Cancelable
import com.mirego.trikot.streams.cancelable.CancelableManager
import spock.lang.Specification

class CancelableManagerTests extends Specification {

    def '''
        given cancelableManager with a cancelable
        when cancelled
        then cancel is called
        '''() {
        given:
        CancelableManager cancelableManager = new CancelableManager()
        Cancelable cancelable = Mock(Cancelable)
        cancelableManager.add(cancelable)

        when:
        cancelableManager.cancel()

        then:
        1 * cancelable.cancel()
    }

    def '''
        given cancelled cancelableManager
        when adding a cancelable
        then cancel is called
        '''() {
        given:
        CancelableManager cancelableManager = new CancelableManager()
        cancelableManager.cancel()
        Cancelable cancelable = Mock(Cancelable)

        when:
        cancelableManager.add(cancelable)

        then:
        1 * cancelable.cancel()
    }
}
