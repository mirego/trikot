package com.mirego.trikot.streams.cancellable

import kotlin.test.Test
import kotlin.test.assertTrue

class CancellableManagerTests {
    @Test
    fun cancelChildWhenCancelled() {
        var cancelled = false
        val cancellableManager = CancellableManager()
        cancellableManager.add { cancelled = true }
        cancellableManager.cancel()

        assertTrue { cancelled }
    }

    @Test
    fun cancelChildWhenAlreadyCancelled() {
        var cancelled = false
        val cancellableManager = CancellableManager()
        cancellableManager.cancel()
        cancellableManager.add { cancelled = true }

        assertTrue { cancelled }
    }
}
