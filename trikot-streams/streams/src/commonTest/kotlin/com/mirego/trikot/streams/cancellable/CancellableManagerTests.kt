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
    fun ifRemoveIsCalled_thenChildIsNotCalledOnParentCancellation() {
        val parentCancellableManager = CancellableManager()
        var cancelCounter = 0

        val child1 = TestableCancellable(onCancelCalled = { cancelCounter++ })
        val child2 = TestableCancellable(onCancelCalled = { cancelCounter++ })
        val child3 = TestableCancellable(onCancelCalled = { cancelCounter++ })

        parentCancellableManager.add(child1)
        parentCancellableManager.add(child2)
        parentCancellableManager.add(child3)

        // Remove child2 before parent cancellation
        parentCancellableManager.remove(child2)

        parentCancellableManager.cancel()

        // Only child1 and child3 should be cancelled
        assertEquals(2, cancelCounter)
        assertTrue(child1.isCancelled)
        assertFalse(child2.isCancelled)
        assertTrue(child3.isCancelled)
    }

    @Test
    fun ifRemoveIsCalledOnAlreadyCancelledManager_thenNothingHappens() {
        val parentCancellableManager = CancellableManager()
        var cancelCounter = 0

        val child = TestableCancellable(onCancelCalled = { cancelCounter++ })
        parentCancellableManager.add(child)

        parentCancellableManager.cancel()

        assertEquals(1, cancelCounter)
        assertTrue(child.isCancelled)

        // Removing after cancellation should not cause issues
        parentCancellableManager.remove(child)

        // Cancel counter should still be 1
        assertEquals(1, cancelCounter)
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
