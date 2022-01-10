package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.sample
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.utils.MockTimer
import com.mirego.trikot.streams.utils.MockTimerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.seconds

class SampleProcessorTests {
    private val firstMockTimer = MockTimer()
    private val secondMockTimer = MockTimer()
    private var timeGetCount = 0
    private val mockTimerFactory = MockTimerFactory { _, _ ->
        timeGetCount += 1
        if (timeGetCount == 1) {
            firstMockTimer
        } else {
            secondMockTimer
        }
    }

    @Test
    fun givenSubscribeToPublisherWithValueWhenSampleThenValueIsDispatchedWhenIntervalIsReached() {
        val publisher = Publishers.behaviorSubject(1)
        val publishedValues = mutableListOf<Int>()

        publisher
            .sample(Duration.seconds(1), mockTimerFactory)
            .subscribe(CancellableManager()) {
                publishedValues.add(it)
            }

        assertEquals(emptyList<Int>(), publishedValues)
        firstMockTimer.executeBlock()
        assertEquals(listOf(1), publishedValues)
    }

    @Test
    fun givenAPublisherThatEmitsTwoValuesWithinAnIntervalWhenSampleThenOnlyLastValueIsDispatched() {
        val publisher = Publishers.behaviorSubject(1)
        val publishedValues = mutableListOf<Int>()

        publisher
            .sample(Duration.seconds(1), mockTimerFactory)
            .subscribe(CancellableManager()) {
                publishedValues.add(it)
            }

        firstMockTimer.executeBlock()
        publisher.value = 2
        publisher.value = 3
        firstMockTimer.executeBlock()

        assertEquals(listOf(1, 3), publishedValues)
    }

    @Test
    fun givenAPublisherThatEmitsTheSameValueInTwoIntervalsWhenSampleThenTheSameValueIsDispatched() {
        val publisher = Publishers.behaviorSubject(1)
        val publishedValues = mutableListOf<Int>()

        publisher
            .sample(Duration.seconds(1), mockTimerFactory)
            .subscribe(CancellableManager()) {
                publishedValues.add(it)
            }

        firstMockTimer.executeBlock()
        publisher.value = 2
        firstMockTimer.executeBlock()
        publisher.value = 2
        firstMockTimer.executeBlock()

        assertEquals(listOf(1, 2, 2), publishedValues)
    }

    @Test
    fun givenAPublisherThatDoesNotEmitsWithinAnIntervalWhenSampleThenTheSameValueIsNotDispatched() {
        val publisher = Publishers.behaviorSubject(1)
        val publishedValues = mutableListOf<Int>()

        publisher
            .sample(Duration.seconds(1), mockTimerFactory)
            .subscribe(CancellableManager()) {
                publishedValues.add(it)
            }

        firstMockTimer.executeBlock()
        publisher.value = 2
        firstMockTimer.executeBlock()
        firstMockTimer.executeBlock()

        assertEquals(listOf(1, 2), publishedValues)
    }

    @Test
    fun givenAPublisherThatDoesNotEmitValueWithinAnIntervalAndASubscriptionIsMadeAfterWhenSampleThenItReceivesTheLastValue() {
        val publisher = Publishers.behaviorSubject(1)
        val firstSubscriptionPublishedValues = mutableListOf<Int>()
        val secondSubscriptionPublishedValues = mutableListOf<Int>()

        val samplePublisher = publisher.sample(Duration.seconds(1), mockTimerFactory)

        samplePublisher.subscribe(CancellableManager()) {
            firstSubscriptionPublishedValues.add(it)
        }

        firstMockTimer.executeBlock()
        publisher.value = 2
        firstMockTimer.executeBlock()

        samplePublisher.subscribe(CancellableManager()) {
            secondSubscriptionPublishedValues.add(it)
        }

        firstMockTimer.executeBlock()
        secondMockTimer.executeBlock()

        assertEquals(listOf(1, 2), firstSubscriptionPublishedValues)
        assertEquals(listOf(2), secondSubscriptionPublishedValues)
    }

    @Test
    fun givenAPublisherThatEmitsAValueAndCompletesBeforeAnIntervalIsReachedThenTheLastValueIsDispatched() {
        val publisher = Publishers.behaviorSubject(1)
        val publishedValues = mutableListOf<Int>()

        publisher
            .sample(Duration.seconds(1), mockTimerFactory)
            .subscribe(CancellableManager()) {
                publishedValues.add(it)
            }

        firstMockTimer.executeBlock()
        publisher.value = 2
        publisher.complete()

        assertEquals(listOf(1, 2), publishedValues)
    }

    @Test
    fun whenCancelSampleThenTimerIsAlsoCanceled() {
        val publisher = Publishers.behaviorSubject(1)
        val publishedValues = mutableListOf<Int>()
        val cancellableManager = CancellableManager()

        publisher
            .sample(Duration.seconds(1), mockTimerFactory)
            .subscribe(cancellableManager) {
                publishedValues.add(it)
            }

        cancellableManager.cancel()

        assertTrue(firstMockTimer.isCancelled)
    }
}
