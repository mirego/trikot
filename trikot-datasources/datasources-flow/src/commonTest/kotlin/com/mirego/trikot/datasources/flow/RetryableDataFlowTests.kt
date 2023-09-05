package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RetryableDataFlowTests {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = CoroutineScope(testDispatcher)

    @Test
    fun retriesIfError() = runTest {
        var callCount = 0
        val error = Throwable()
        val retryableDataFlow = RetryableDataFlow<String>(testScope) {
            callCount++
            flowOf(DataState.Error(error))
        }
        val values = mutableListOf<DataState<String, Throwable>>()
        val job = launch(testDispatcher) {
            retryableDataFlow.toList(values)
        }
        retryableDataFlow.retryIfError()
        retryableDataFlow.retryIfError()
        assertEquals(listOf(DataState.Pending(), DataState.Error(error)), values)
        assertEquals(3, callCount)
        job.cancel()
    }

    @Test
    fun doesntRetryOnData() = runTest {
        var callCount = 0
        val retryableDataFlow = RetryableDataFlow(testScope) {
            callCount++
            flowOf(DataState.Data(callCount.toString()))
        }
        val values = mutableListOf<DataState<String, Throwable>>()
        val job = launch(testDispatcher) {
            retryableDataFlow.toList(values)
        }
        retryableDataFlow.retry()
        retryableDataFlow.retryIfError()
        assertEquals(listOf(DataState.Pending(), DataState.Data("1"), DataState.Data("2")), values)
        assertEquals(2, callCount)
        job.cancel()
    }

    @Test
    fun keepsPreviousValuesOnError() = runTest {
        var callCount = 0
        val error = Throwable()
        val retryableDataFlow = RetryableDataFlow(testScope) {
            callCount++
            when (callCount) {
                1 -> flowOf(DataState.Data(callCount.toString()))
                else -> flowOf(DataState.Error(error))
            }
        }
        val values = mutableListOf<DataState<String, Throwable>>()
        val job = launch(testDispatcher) {
            retryableDataFlow.toList(values)
        }
        retryableDataFlow.retry()
        assertEquals(listOf(DataState.Pending(), DataState.Data("1"), DataState.Error(error, "1")), values)
        assertEquals(2, callCount)
        job.cancel()
    }
}
