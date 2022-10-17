package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.DataState
import com.mirego.trikot.foundation.date.Date
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.CoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals

class BaseExpiringExecutableFlowDataSourceTests {
    private val requestUseCache = BasicRequest("1", FlowDataSourceRequest.Type.USE_CACHE, 1000)
    private val requestRefreshCache = BasicRequest("1", FlowDataSourceRequest.Type.REFRESH_CACHE, 1000)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun whenCacheIsExpiredThenReadReturnsPendingWithCacheData() = runTest {
        val initialData = TestData("value")
        val cacheDataSource = CacheTestDataSource(FlowDataSourceExpiringValue(initialData, 0), testDispatcher)
        val mainDataSource = TestDataSource({ awaitCancellation() }, cacheDataSource, testDispatcher)

        assertEquals(DataState.pending(initialData), mainDataSource.readValue(requestUseCache).first())
    }

    @Test
    fun whenCacheIsValidThenReadReturnsCacheData() = runTest {
        val initialData = TestData("value")
        val cacheDataSource = CacheTestDataSource(FlowDataSourceExpiringValue(initialData, Date.now.epoch + 1000), testDispatcher)
        val mainDataSource = TestDataSource({ awaitCancellation() }, cacheDataSource, testDispatcher)

        assertEquals(DataState.data(initialData), mainDataSource.readValue(requestUseCache).first())
    }

    @Test
    fun whenCacheIsExpiredAndNewReadFailedThenErrorWithCacheData() = runTest {
        val initialData = TestData("value")
        val cacheDataSource = CacheTestDataSource(FlowDataSourceExpiringValue(initialData, 0), testDispatcher)
        val error = Throwable()
        val mainDataSource = TestDataSource({ throw error }, cacheDataSource, testDispatcher)

        assertEquals(DataState.error(error, initialData), mainDataSource.readValue(requestUseCache).first())
    }

    @Test
    fun whenCacheIsExpiredThenReadReturnsCacheDataThenReadCompleteWithDataThenNewDataReturns() = runTest {
        val initialData = TestData("value")
        val newData = TestData("new data")
        val mutex = Mutex(locked = true)
        val cacheDataSource = CacheTestDataSource(FlowDataSourceExpiringValue(initialData, 0), testDispatcher)
        val mainDataSource = TestDataSource({
            mutex.withLock {
                FlowDataSourceExpiringValue(newData, Date.now.epoch + 1000)
            }
        }, cacheDataSource, testDispatcher)

        val values = mutableListOf<DataState<TestData, Throwable>>()
        val job = launch(testDispatcher) {
            mainDataSource.readValue(requestUseCache).toList(values)
        }
        mutex.unlock()
        assertEquals(listOf(DataState.pending(), DataState.pending(initialData), DataState.data(newData)), values)
        job.cancel()
    }

    @Test
    fun whenCacheIsValidThenReadWith_REFRESH_CACHE_ReturnsPendingWithData() = runTest {
        val initialData = TestData("value")
        val cacheDataSource = CacheTestDataSource(FlowDataSourceExpiringValue(initialData, Date.now.epoch + 1000), testDispatcher)
        val mainDataSource = TestDataSource({ awaitCancellation() }, cacheDataSource, testDispatcher)

        assertEquals(DataState.pending(initialData), mainDataSource.readValue(requestRefreshCache).first())
    }
}

private data class TestData(
    val value: String
)

private data class BasicRequest(
    override val cacheableId: String,
    override val requestType: FlowDataSourceRequest.Type,
    override val expiredInMilliseconds: Long
) : ExpiringFlowDataSourceRequest

private class TestDataSource(
    private var readFunction: suspend (request: BasicRequest) -> FlowDataSourceExpiringValue<TestData>,
    cacheDataSource: CacheTestDataSource? = null,
    coroutineContext: CoroutineContext
) :
    BaseExpiringExecutableFlowDataSource<BasicRequest, TestData>(cacheDataSource, coroutineContext) {

    var internalReadCount = 0

    override suspend fun internalRead(request: BasicRequest): FlowDataSourceExpiringValue<TestData> {
        internalReadCount++
        return readFunction(request)
    }
}

private class CacheTestDataSource(
    private var savedValue: FlowDataSourceExpiringValue<TestData>? = null,
    coroutineContext: CoroutineContext
) :
    BaseExpiringExecutableFlowDataSource<BasicRequest, TestData>(coroutineContext = coroutineContext) {

    override suspend fun internalRead(request: BasicRequest): FlowDataSourceExpiringValue<TestData> {
        return savedValue ?: throw Throwable()
    }

    override suspend fun save(request: BasicRequest, data: FlowDataSourceExpiringValue<TestData>?) {
        savedValue = data
    }
}
