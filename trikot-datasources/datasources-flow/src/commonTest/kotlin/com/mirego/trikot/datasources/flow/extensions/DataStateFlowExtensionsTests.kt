package com.mirego.trikot.datasources.flow.extensions

import com.mirego.trikot.datasources.DataState
import com.mirego.trikot.datasources.extensions.mapData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DataStateFlowExtensionsTests {
    private val combineTransform: suspend (a: String, b: String) -> String = { a, b -> a + b }

    @Test
    fun combineDataGiven2DataItIsTransformedToData() = runTest {
        val a = flowOf(DataState.data<String, Throwable>("a"))
        val b = flowOf(DataState.data<String, Throwable>("b"))

        val result = a.combineData(b, combineTransform).first()
        assertEquals(DataState.data("ab"), result)
    }

    @Test
    fun combineDataGiven1Data1ErrorItIsTransformedToErrorWithData() = runTest {
        val error = Throwable()
        val a = flowOf(DataState.data<String, Throwable>("a"))
        val b = flowOf(DataState.error(error, "b"))

        val result = a.combineData(b, combineTransform).first()
        assertEquals(DataState.error(error, "ab"), result)
    }

    @Test
    fun combineDataGiven1Pending1ErrorWithDataItIsTransformedToPendingWithData() = runTest {
        val error = Throwable()
        val a = flowOf(DataState.pending<String, Throwable>("a"))
        val b = flowOf(DataState.error(error, "b"))

        val result = a.combineData(b, combineTransform).first()
        assertEquals(DataState.pending("ab"), result)
    }

    @Test
    fun combineDataGiven1PendingWithoutData1DataItIsTransformedToPending() = runTest {
        val a = flowOf(DataState.pending<String, Throwable>())
        val b = flowOf(DataState.data<String, Throwable>("b"))

        val result = a.combineData(b, combineTransform).first()
        assertEquals(DataState.pending(), result)
    }

    @Test
    fun combineDataGiven1PendingWithoutData1ErrorItIsTransformedToPending() = runTest {
        val a = flowOf(DataState.pending<String, Throwable>())
        val b = flowOf(DataState.error(Throwable(), "b"))

        val result = a.combineData(b, combineTransform).first()
        assertEquals(DataState.pending(), result)
    }

    @Test
    fun mapValueMapsTheDataStateValueInAllStates() = runTest {
        val transform: suspend (a: String) -> String = { a -> "$a$a" }
        val error = Throwable()
        assertEquals(DataState.pending(), flowOf(DataState.pending<String, Throwable>()).mapValue(transform).first())
        assertEquals(DataState.pending("aa"), flowOf(DataState.pending<String, Throwable>("a")).mapValue(transform).first())
        assertEquals(DataState.error(error), flowOf(DataState.error<String, Throwable>(error)).mapValue(transform).first())
        assertEquals(DataState.error(error, "aa"), flowOf(DataState.error(error, "a")).mapValue(transform).first())
        assertEquals(DataState.data("aa"), flowOf(DataState.data<String, Throwable>("a")).mapValue(transform).first())
    }
}