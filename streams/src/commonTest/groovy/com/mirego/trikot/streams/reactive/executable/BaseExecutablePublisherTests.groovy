package com.mirego.trikot.streams.reactive.executable

import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.concurrent.dispatchQueue.SynchronousDispatchQueue
import org.jetbrains.annotations.NotNull
import spock.lang.Specification

class BaseExecutablePublisherTests extends Specification {

    def '''
        given operation
        when starting
        then error is thrown
        '''() {
        given:
        FakeExecutablePublisher executable = new FakeExecutablePublisher(null, null)

        when:
        executable.execute()
        executable.execute()

        then:
        thrown AlreadyStartedException
    }

    def '''
        given operation
        when cancelled
        then cancellable manager is called
        '''() {
        given:
        FakeExecutablePublisher executable = new FakeExecutablePublisher(null, null)
        executable.execute()

        when:
        executable.cancel()

        then:
        executable.isCancelled
    }

    def '''
        given operation
        when success is dispatched by the implementation
        then success value set
        '''() {
        given:
        FakeExecutablePublisher executable = new FakeExecutablePublisher("Success", null)

        when:
        executable.execute()

        then:
        executable.value == "Success"
    }

    def '''
        given operation
        when error is dispatched by the implementation
        then error value is set
        '''() {
        given:
        Throwable expectedError = new Throwable()
        FakeExecutablePublisher executable = new FakeExecutablePublisher(null, expectedError)

        when:
        executable.execute()

        then:
        executable.error == expectedError
    }

    class FakeExecutablePublisher extends BaseExecutablePublisher<String> {
        String successValue = null
        Throwable errorValue = null
        Boolean isCancelled = false

        FakeExecutablePublisher(String success, Throwable error) {
            super(new SynchronousDispatchQueue())
            this.successValue = success
            this.errorValue = error
        }

        @Override
        void internalRun(@NotNull CancellableManager cancellableManager) {
            if (successValue != null) {
                dispatchSuccess(successValue)
            } else if (errorValue != null) {
                dispatchError(errorValue)
            }

            cancellableManager.add(new Cancellable() {
                @Override
                void cancel() {
                    isCancelled = true
                }
            })

        }
    }
}
