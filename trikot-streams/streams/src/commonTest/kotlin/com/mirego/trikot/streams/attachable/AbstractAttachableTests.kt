package com.mirego.trikot.streams.attachable

import com.mirego.trikot.streams.cancellable.CancellableManager
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AbstractAttachableTests {
    private var attachable = AttachableMock()

    @BeforeTest
    fun setup() {
        attachable = AttachableMock()
    }

    @Test
    fun attachingOnlyAttachOnce() {
        attachable.attach()
        attachable.attach()
        assertEquals(1, attachable.attachCount)
    }

    @Test
    fun detachingMultipleTimeOnlyDetachOnce() {
        val cancellable1 = attachable.attach()
        val cancellable2 = attachable.attach()
        cancellable1.cancel()
        cancellable2.cancel()

        assertEquals(1, attachable.attachCount)
        assertEquals(1, attachable.detachCount)
    }

    @Test
    fun stayAttachedWhenOnlyOneIsDetached() {
        val cancellable1 = attachable.attach()
        attachable.attach()
        cancellable1.cancel()

        assertEquals(1, attachable.attachCount)
        assertEquals(0, attachable.detachCount)
    }

    @Test
    fun attachIsCalledAgainWhenReattaching() {
        attachable.attach().cancel()
        attachable.attach()

        assertEquals(2, attachable.attachCount)
        assertEquals(1, attachable.detachCount)
    }

    @Test
    fun execeptionIsThrownWhenMaxAttachCountIsReached() {
        attachable = AttachableMock(1)
        attachable.attach()
        assertFailsWith(IllegalStateException::class) {
            attachable.attach()
        }
    }

    class AttachableMock(max: Int = Int.MAX_VALUE) : AbstractAttachable(max) {
        var attachCount = 0
        var detachCount = 0
        var lastCancellableManager = CancellableManager()

        override fun doAttach(cancellableManager: CancellableManager) {
            super.doAttach(cancellableManager)
            lastCancellableManager = cancellableManager
            attachCount++
        }

        override fun doDetach() {
            super.doDetach()
            detachCount++
        }
    }
}
