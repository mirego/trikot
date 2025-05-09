package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.tests.MockPublisher
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CombineLatestProcessorTests {
    @Test
    fun noValueAreDispatchedWhenMissing() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher()
        var firstValueReceived: String? = null
        var secondValueReceived: String? = null

        firstPublisher.combine(secondPublisher)
            .subscribe(CancellableManager()) { (value1, value2) ->
                firstValueReceived = value1
                secondValueReceived = value2
            }

        assertNull(firstValueReceived)
        assertNull(secondValueReceived)
    }

    @Test
    fun valuesAreDispatchedWhenAllPublishersEmits() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")
        val thirdPublisher = MockPublisher("c")
        var firstValueReceived: String? = null
        var secondValueReceived: String? = null
        var thirdValueReceived: String? = null

        firstPublisher.combine(listOf(secondPublisher, thirdPublisher))
            .subscribe(CancellableManager()) { (value1, value2, value3) ->
                firstValueReceived = value1
                secondValueReceived = value2
                thirdValueReceived = value3
            }

        assertEquals("a", firstValueReceived)
        assertEquals("b", secondValueReceived)
        assertEquals("c", thirdValueReceived)
    }

    @Test
    fun completedStateValidationPublishersEmits() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher()
        val thirdPublisher = MockPublisher("b")

        var valuesReceived: List<String?>? = null
        var completed = false

        combine(listOf(firstPublisher, secondPublisher, thirdPublisher)).subscribe(
            CancellableManager(),
            onNext = { valuesReceived = it },
            onError = {},
            onCompleted = { completed = true }
        )

        firstPublisher.complete()
        secondPublisher.complete()
        thirdPublisher.complete()

        assertEquals(listOf("a", null, "b"), valuesReceived)
        assertTrue(completed)
    }

    @Test
    fun valuesAreRedispatchedWhenAPublisherReemits() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")
        var firstValueReceived: String? = null
        var secondValueReceived: String? = null

        firstPublisher.combine(secondPublisher)
            .subscribe(CancellableManager()) { (value1, value2) ->
                firstValueReceived = value1
                secondValueReceived = value2
            }
        secondPublisher.value = "c"

        assertEquals("a", firstValueReceived)
        assertEquals("c", secondValueReceived)
    }

    @Test
    fun subscriptionsAreCancelledWhenNoOneIsConnected() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")
        val cancellableManager = CancellableManager()

        firstPublisher.combine(secondPublisher).subscribe(cancellableManager) {}
        cancellableManager.cancel()

        assertFalse { firstPublisher.getHasSubscriptions }
        assertFalse { secondPublisher.getHasSubscriptions }
    }

    @Test
    fun subscribeToChildOnlyWhenSubscriptionsAreActive() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")

        firstPublisher.combine(secondPublisher)

        assertFalse { firstPublisher.getHasSubscriptions }
        assertFalse { secondPublisher.getHasSubscriptions }
    }

    @Test
    fun whenErrorIsEmittedByOnePublisherAllSubscriptionsAreCancelled() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")
        val expectedError = Exception()
        var receivedError: Throwable? = null

        firstPublisher.combine(secondPublisher).subscribe(CancellableManager(), onNext = {}) {
            receivedError = it
        }
        secondPublisher.error = expectedError

        assertEquals(expectedError, receivedError)
        assertFalse { firstPublisher.getHasSubscriptions }
        assertFalse { secondPublisher.getHasSubscriptions }
    }

    @Test
    fun valuesAreNotReemittedOnCompletion() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")
        firstPublisher.complete()
        secondPublisher.complete()

        var callbackCount = 0
        firstPublisher.combine(secondPublisher).subscribe(CancellableManager()) {
            callbackCount++
        }
        assertEquals(1, callbackCount)
    }

    @Test
    fun onlyOneErrorIsDispatched() {
        val firstPublisher = MockPublisher("a").also { it.error = Throwable() }
        val secondPublisher = MockPublisher("b").also { it.error = Throwable() }
        val thirdPublisher = MockPublisher("b").also { it.error = Throwable() }

        var errorCount = 0
        combine(listOf(firstPublisher, secondPublisher, thirdPublisher)).subscribe(
            CancellableManager(),
            onNext = {},
            onError = { errorCount++ }
        )

        assertEquals(1, errorCount)
    }

    @Test
    fun whenUsingSafeCombineNoValueAreDispatchedWhenMissing() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher()
        var didDispatch = false
        firstPublisher.safeCombine(secondPublisher)
            .subscribe(CancellableManager()) {
                didDispatch = true
            }
        assertFalse(didDispatch)
    }

    @Test
    fun whenUsingSafeCombineValuesAreDispatchedWhenAllPublishersEmits() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")
        var firstValueReceived: String? = null
        var secondValueReceived: String? = null

        firstPublisher.safeCombine(secondPublisher)
            .subscribe(CancellableManager()) { (value1, value2) ->
                firstValueReceived = value1
                secondValueReceived = value2
            }

        assertEquals("a", firstValueReceived)
        assertEquals("b", secondValueReceived)
    }

    @Test
    fun whenUsingSafeCombineWith2ArgumentsValuesAreDispatchedWhenAllPublishersEmits() {
        val firstPublisher = MockPublisher("a")
        val secondPublisher = MockPublisher("b")
        val thirdPublisher = MockPublisher("c")
        var firstValueReceived: String? = null
        var secondValueReceived: String? = null
        var thirdValueReceived: String? = null

        firstPublisher.safeCombine(secondPublisher, thirdPublisher)
            .subscribe(CancellableManager()) { (value1, value2, value3) ->
                firstValueReceived = value1
                secondValueReceived = value2
                thirdValueReceived = value3
            }

        assertEquals("a", firstValueReceived)
        assertEquals("b", secondValueReceived)
        assertEquals("c", thirdValueReceived)
    }

    @Test
    fun combineWithMultiplePublishers_initializesStateSafelyAndCombinesValues() {
        val publishers = listOf(
            MockPublisher("a1"),
            MockPublisher("b1"),
            MockPublisher("c1"),
            MockPublisher("d1"),
            MockPublisher("e1")
        )

        val receivedValues = mutableMapOf<String, MutableList<String?>>()

        val totalCombinations = 5
        for (i in 0 until totalCombinations) {
            val secondIndex = (i + 1) % publishers.size
            val thirdIndex = (i + 2) % publishers.size

            val mainPublisher = publishers[i]
            val secondPublisher = publishers[secondIndex]
            val thirdPublisher = publishers[thirdIndex]

            val combinationId = "combo_$i"
            receivedValues[combinationId] = mutableListOf(null, null, null)

            mainPublisher.safeCombine(secondPublisher, thirdPublisher)
                .subscribe(CancellableManager()) { (value1, value2, value3) ->
                    receivedValues[combinationId]?.let {
                        it[0] = value1
                        it[1] = value2
                        it[2] = value3
                    }
                }

            publishers.forEach { publisher ->
                publisher.value += "_updated"
            }
        }

        publishers.forEachIndexed { index, publisher ->
            publisher.value = "final_$index"
        }

        for (i in 0 until totalCombinations) {
            val secondIndex = (i + 1) % publishers.size
            val thirdIndex = (i + 2) % publishers.size

            val values = receivedValues["combo_$i"] ?: error("Missing values for combination $i")

            assertEquals("final_$i", values[0])
            assertEquals("final_$secondIndex", values[1])
            assertEquals("final_$thirdIndex", values[2])
        }
    }

}
