package com.mirego.trikot.streams.reactive.executable

import com.mirego.trikot.foundation.concurrent.dispatchQueue.SynchronousDispatchQueue
import com.mirego.trikot.streams.cancellable.CancellableManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BaseExecutablePublisherTests {
    @Test
    fun alreadyStartedExceptionTest() {
        val executable = FakeExecutablePublisher()
        executable.execute()
        assertFailsWith(AlreadyStartedException::class) {
            executable.execute()
        }
    }

    @Test
    fun cancelCancelsExecutionTest() {
        val executable = FakeExecutablePublisher()
        executable.execute()
        executable.cancel()

        assertTrue { executable.isCancelled }
    }

    @Test
    fun successDispatchSuccessValue() {
        val executable = FakeExecutablePublisher("a")
        executable.execute()

        assertTrue { executable.isCancelled }
        assertEquals("a", executable.value)
    }

    @Test
    fun errorDispatchErrorValue() {
        val error = Exception()
        val executable = FakeExecutablePublisher(errorValue = error)
        executable.execute()

        assertTrue { executable.isCancelled }
        assertEquals(error, executable.error)
    }

    class FakeExecutablePublisher(val successValue: String? = null, val errorValue: Throwable? = null) : BaseExecutablePublisher<String>(SynchronousDispatchQueue()) {
        var isCancelled = false

        override fun internalRun(cancellableManager: CancellableManager) {
            cancellableManager.add { isCancelled = true }
            successValue?.let {
                dispatchSuccess(successValue)
            } ?: errorValue?.let {
                dispatchError(errorValue)
            }
        }
    }
}
