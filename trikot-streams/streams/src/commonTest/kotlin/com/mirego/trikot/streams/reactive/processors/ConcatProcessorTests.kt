package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.reactive.BehaviorSubject
import com.mirego.trikot.streams.reactive.PublisherResultAccumulator
import com.mirego.trikot.streams.reactive.Publishers
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ConcatProcessorTests {
    lateinit var firstPublisher: BehaviorSubject<String>
    lateinit var secondPublisher: BehaviorSubject<String>
    lateinit var processor: ConcatProcessor<String>
    lateinit var accumulator: PublisherResultAccumulator<String>

    @BeforeTest
    fun setup() {
        firstPublisher = Publishers.behaviorSubject()
        secondPublisher = Publishers.behaviorSubject()
        processor = ConcatProcessor(firstPublisher, secondPublisher)
        accumulator = PublisherResultAccumulator(processor)
    }

    @Test
    fun noValueAreDispatchedFromSecondObservableWhenFirstAsNotCompleted() {
        firstPublisher.value = "a"
        secondPublisher.value = "1"
        firstPublisher.value = "b"
        assertEquals(listOf("a", "b"), accumulator.values)
        assertEquals(false, accumulator.completed)
        assertEquals(null, accumulator.error)
    }

    @Test
    fun valuesFromSecondPublisherAreDispatchedAfterFirstPublisherCompleted() {
        firstPublisher.value = "a"
        secondPublisher.value = "1"
        firstPublisher.value = "b"
        firstPublisher.complete()
        assertEquals(listOf("a", "b", "1"), accumulator.values)
        assertEquals(false, accumulator.completed)
        assertEquals(null, accumulator.error)
    }

    @Test
    fun valuesFromSecondPublisherAreBufferedUntilFirstPublisherCompleted() {
        firstPublisher.value = "a"
        secondPublisher.value = "1"
        secondPublisher.value = "2"
        firstPublisher.value = "b"
        firstPublisher.complete()
        assertEquals(listOf("a", "b", "2"), accumulator.values)
        assertEquals(false, accumulator.completed)
        assertEquals(null, accumulator.error)
    }

    @Test
    fun completedWhenBothPublisherAreCompleted() {
        firstPublisher.value = "a"
        secondPublisher.value = "1"
        secondPublisher.complete()
        firstPublisher.value = "b"
        firstPublisher.complete()
        assertEquals(listOf("a", "b", "1"), accumulator.values)
        assertEquals(true, accumulator.completed)
        assertEquals(null, accumulator.error)
    }

    @Test
    fun dispatchErrorsFromTheFirstSubscriber() {
        firstPublisher.value = "a"
        secondPublisher.value = "1"
        firstPublisher.value = "b"
        firstPublisher.error = Throwable()
        assertEquals(listOf("a", "b"), accumulator.values)
        assertEquals(false, accumulator.completed)
        assertEquals(firstPublisher.error, accumulator.error)
    }

    @Test
    fun dispatchAllValuesFromTheFirstPublisherBeforeDispatchingTheErrorFromTheSecondPublisher() {
        firstPublisher.value = "a"
        secondPublisher.value = "1"
        secondPublisher.error = Throwable()
        firstPublisher.value = "b"
        firstPublisher.complete()
        assertEquals(listOf("a", "b"), accumulator.values)
        assertEquals(false, accumulator.completed)
        assertEquals(secondPublisher.error, accumulator.error)
    }
}
