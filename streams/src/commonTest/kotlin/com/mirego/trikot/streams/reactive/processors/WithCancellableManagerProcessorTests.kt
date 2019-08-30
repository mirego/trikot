package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.reactive.withCancellableManager
import kotlin.test.Test
import kotlin.test.assertTrue

class WithCancellableManagerProcessorTests {
    @Test
    fun cancelPreviousCancellableManager() {
        val publisher = Publishers.behaviorSubject("a")
        var firstValueReceived = false
        var isCancelled = false

        publisher.withCancellableManager().subscribe(CancellableManager()) { (cancellableManager, _) ->
            if (!firstValueReceived) {
                firstValueReceived = true
                cancellableManager.add { isCancelled = true }
            }
        }

        publisher.value = "b"

        assertTrue { isCancelled }
    }
}
