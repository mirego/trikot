package com.mirego.trikot.streams.cancellable

import kotlin.test.Test
import kotlin.test.assertTrue

class CancellableManagerProviderTests {

    @Test
    fun previousCancellableIsCancelledWhenProvidingANewOne() {
        val cancellableManagerProvider = CancellableManagerProvider()
        var cancelled = false
        cancellableManagerProvider.cancelPreviousAndCreate().add { cancelled = true }
        cancellableManagerProvider.cancelPreviousAndCreate()

        assertTrue { cancelled }
    }

    @Test
    fun previousCancellableIsCancelledWhenProviderIsCancelled() {
        val cancellableManagerProvider = CancellableManagerProvider()
        var cancelled = false
        cancellableManagerProvider.cancelPreviousAndCreate().add { cancelled = true }
        cancellableManagerProvider.cancel()

        assertTrue { cancelled }
    }
}
