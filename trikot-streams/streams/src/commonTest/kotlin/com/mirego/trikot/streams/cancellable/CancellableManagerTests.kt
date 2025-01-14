package com.mirego.trikot.streams.cancellable

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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

    @Test
    fun ifCleanCancelledChildrenIsNotCalled_thenAllChildAreCalledOnParentCancellation() {
        val parentCancellableManager = CancellableManager()
        val childList = mutableListOf<TestableCancellable>()
        var cancelCounter = 0

        for (i in 1..3) {
            val cancellableManager = TestableCancellable(
                onCancelCalled = { cancelCounter++ }
            )

            childList.add(cancellableManager)
            parentCancellableManager.add(cancellableManager)
        }

        // We have 3 cancellable children
        assertEquals(3, childList.size)

        // Cancel first 2 children
        childList[0].cancel()
        childList[1].cancel()

        assertEquals(2, cancelCounter)

        parentCancellableManager.cancel()

        // All 3 children are called, so 5 total calls
        assertEquals(5, cancelCounter)
    }

    @Test
    fun ifCleanCancelledChildrenIsCalled_thenOnlyCancelRemainingChildOnParentCancellation() {
        val parentCancellableManager = CancellableManager()
        val childList = mutableListOf<TestableCancellable>()
        var cancelCounter = 0

        for (i in 1..3) {
            val cancellableManager = TestableCancellable(
                onCancelCalled = { cancelCounter++ }
            )

            childList.add(cancellableManager)
            parentCancellableManager.add(cancellableManager)
        }

        // We have 3 cancellable children
        assertEquals(3, childList.size)

        var expectedCancelCalls = 0
        assertEquals(expectedCancelCalls, cancelCounter)

        // Cancel first 2 children
        childList[0].cancel()
        childList[1].cancel()

        expectedCancelCalls += 2
        assertEquals(2, cancelCounter)
        assertTrue(childList[0].isCancelled)
        assertTrue(childList[1].isCancelled)
        assertFalse(childList[2].isCancelled)

        parentCancellableManager.cleanCancelledChildren()

        // Nothing has changed
        assertEquals(expectedCancelCalls, cancelCounter)
        assertTrue(childList[0].isCancelled)
        assertTrue(childList[1].isCancelled)
        assertFalse(childList[2].isCancelled)

        parentCancellableManager.cancel()

        // Only 1 child is called on parent cancellation since the other 2 have been cleaned
        expectedCancelCalls++
        assertEquals(expectedCancelCalls, cancelCounter)
        assertTrue(childList[0].isCancelled)
        assertTrue(childList[1].isCancelled)
        assertTrue(childList[2].isCancelled)
    }

    private class TestableCancellable(
        private val onCancelCalled: (() -> Unit)? = null
    ) : Cancellable, VerifiableCancelledState {
        override var isCancelled = false

        override fun cancel() {
            isCancelled = true
            onCancelCalled?.invoke()
        }
    }
}
