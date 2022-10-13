package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.DataState
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BaseFlowDataSourceTests {
    private val requestUseCache = TestDataSourceRequest("1", FlowDataSourceRequest.Type.USE_CACHE, DataSourceTestData("1"))
    private val requestRefreshCache = TestDataSourceRequest("1", FlowDataSourceRequest.Type.REFRESH_CACHE, DataSourceTestData("1"))
    private val requestUseCache2 = TestDataSourceRequest("2", FlowDataSourceRequest.Type.USE_CACHE, DataSourceTestData("2"))
    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun whenCacheHasDataThenReadReturnsCacheDataAndInternalReadNotCalled() = runTest {
        val initialData = DataSourceTestData("value")
        val cacheDataSource = CacheDataSource({ initialData }, testDispatcher)
        val mainDataSource = MainDataSource({ awaitCancellation() }, cacheDataSource, testDispatcher)

        assertEquals(DataState.data(initialData), mainDataSource.read(requestUseCache).first())
        assertEquals(0, mainDataSource.internalReadCount)
    }

    @Test
    fun whenCacheIsValidThenReadWith_REFRESH_CACHE_ReturnsPendingWithDataAndInternalReadCalled() = runTest {
        val initialData = DataSourceTestData("value")
        val cacheDataSource = CacheDataSource({ initialData }, testDispatcher)
        val mainDataSource = MainDataSource({ awaitCancellation() }, cacheDataSource, testDispatcher)
        assertEquals(DataState.pending(initialData), mainDataSource.read(requestRefreshCache).first())
        assertEquals(1, mainDataSource.internalReadCount)
    }

    @Test
    fun givenPendingCacheDataWhenCacheDataCompletedThenReturningPendingWithCacheDataAndInternalReadCalled() = runTest {
        val cacheData = DataSourceTestData("value")
        val mutex = Mutex(locked = true)

        val cacheDataSource = CacheDataSource({
            mutex.withLock {
                cacheData
            }
        }, testDispatcher)
        val mainDataSource = MainDataSource({ awaitCancellation() }, cacheDataSource, testDispatcher)

        val values = mutableListOf<DataState<DataSourceTestData, Throwable>>()
        val collectJob = launch(testDispatcher) {
            mainDataSource.read(requestRefreshCache).toList(values)
        }

        mutex.unlock()
        assertEquals(listOf(DataState.pending(), DataState.pending(cacheData)), values)
        assertEquals(1, mainDataSource.internalReadCount)
        collectJob.cancel()
    }

    @Test
    fun givenErrorCacheDataThenPendingIsReturned() = runTest {
        val cacheDataSource = CacheDataSource({ throw Throwable() }, testDispatcher)
        val mainDataSource = MainDataSource({ awaitCancellation() }, cacheDataSource, testDispatcher)

        val values = mutableListOf<DataState<DataSourceTestData, Throwable>>()
        val collectJob = launch(testDispatcher) {
            mainDataSource.read(requestUseCache).toList(values)
        }
        assertEquals(listOf(DataState.pending()), values)
        collectJob.cancel()
    }

    @Test
    fun givenPendingCacheDataWhenReadWithRefreshAndCacheDataCompletedAndDataIsAvailableThenReturningTheNewDataAndSaveCalled() = runTest {
        val mutex = Mutex(locked = true)
        val cacheData = DataSourceTestData("value")
        val cacheDataSource = CacheDataSource({
            mutex.withLock {
                cacheData
            }
        }, testDispatcher)
        val newData = DataSourceTestData("newValue")
        val mainDataSource = MainDataSource({ newData }, cacheDataSource, testDispatcher)

        val values = mutableListOf<DataState<DataSourceTestData, Throwable>>()
        val job = launch(testDispatcher) {
            mainDataSource.read(requestRefreshCache).toList(values)
        }
        mutex.unlock()
        assertEquals(listOf(DataState.pending(), DataState.data(newData)), values)
        assertEquals(1, mainDataSource.internalReadCount)
        assertEquals(1, cacheDataSource.internalSaveCount)
        job.cancel()
    }

    @Test
    fun when3ReadAtTheSameTimeThenInternalReadCalledOnlyOnce() = runTest {
        val initialData = DataSourceTestData("value")
        val cacheDataSource = CacheDataSource({ initialData }, testDispatcher)
        val mainDataSource = MainDataSource({ awaitCancellation() }, cacheDataSource, testDispatcher)

        val values1 = mutableListOf<DataState<DataSourceTestData, Throwable>>()
        val job1 = launch(testDispatcher) {
            mainDataSource.read(requestRefreshCache).toList(values1)
        }
        val values2 = mutableListOf<DataState<DataSourceTestData, Throwable>>()
        val job2 = launch(testDispatcher) {
            mainDataSource.read(requestRefreshCache).toList(values2)
        }
        val values3 = mutableListOf<DataState<DataSourceTestData, Throwable>>()
        val job3 = launch(testDispatcher) {
            mainDataSource.read(requestRefreshCache).toList(values3)
        }
        assertEquals(listOf(DataState.Pending(), DataState.pending(initialData)), values1)
        assertEquals(listOf(DataState.pending(initialData)), values2)
        assertEquals(listOf(DataState.pending(initialData)), values3)
        assertEquals(1, mainDataSource.internalReadCount)
        job1.cancel()
        job2.cancel()
        job3.cancel()
    }

    @Test
    fun givenCachedDataWhenRefreshingWithoutAnySubscriberThenNextSubscriberReceiveRefreshedData() = runTest {
        val initialData = DataSourceTestData("value")
        val cacheDataSource = CacheDataSource({ initialData }, testDispatcher)
        val mainDataSource = MainDataSource({ awaitCancellation() }, cacheDataSource, testDispatcher)

        mainDataSource.read(requestUseCache)
        val refreshData = DataSourceTestData("refreshValue")
        mainDataSource.readFunction = { refreshData }
        assertEquals(DataState.data(refreshData), mainDataSource.read(requestRefreshCache).first())
    }

    @Test
    fun whenRefreshingWithCachedDataWhenAnErrorOccursThenCachedDataIsReturnedWithAnError() = runTest {
        val data = DataSourceTestData("data")
        val mainDataSource = MainDataSource({ data }, coroutineContext = testDispatcher)

        assertEquals(DataState.data(data), mainDataSource.read(requestUseCache).first())
        val error = Throwable()
        mainDataSource.readFunction = { throw error }
        assertEquals(DataState.error(error, data), mainDataSource.read(requestRefreshCache).first())
    }

    @Test
    fun whenNoCacheDataSourceThenStartInPendingThenDataReceived() = runTest {
        val data = DataSourceTestData("data")
        val mutex = Mutex(locked = true)
        val mainDataSource = MainDataSource({
            mutex.withLock {
                data
            }
        }, coroutineContext = testDispatcher)

        val values = mutableListOf<DataState<DataSourceTestData, Throwable>>()
        val collectJob = launch(testDispatcher) {
            mainDataSource.read(requestRefreshCache).toList(values)
        }
        assertEquals(DataState.Pending(), values[0])
        mutex.unlock()
        assertEquals(DataState.data(data), values[1])
        collectJob.cancel()
    }

    @Test
    fun when2ReadFromDifferentCacheIdThenDataIsDistinctAndInternalReadCalledTwice() = runTest {
        val mainDataSource = MainDataSource({ throw Throwable() }, userRequestValue = true, coroutineContext = testDispatcher)

        assertEquals(DataState.data(requestUseCache.value), mainDataSource.read(requestUseCache).first())
        assertEquals(DataState.data(requestUseCache2.value), mainDataSource.read(requestUseCache2).first())
        assertEquals(2, mainDataSource.internalReadCount)
    }

    @Test
    fun whenDeleteCacheIdThenInternalReadCalled() = runTest {
        val initialData = DataSourceTestData("value")
        val cacheDataSource = CacheDataSource({ initialData }, testDispatcher)
        val mainDataSource = MainDataSource({ DataSourceTestData("otherValue") }, cacheDataSource, testDispatcher)

        assertEquals(DataState.data(initialData), mainDataSource.read(requestUseCache).first())

        mainDataSource.delete(requestUseCache.cacheableId)
        mainDataSource.readFunction = { awaitCancellation() }
        cacheDataSource.readFunction = { awaitCancellation() }

        assertEquals(DataState.pending(), mainDataSource.read(requestUseCache).first())

        assertEquals(1, cacheDataSource.internalDeleteCount)
        assertEquals(1, mainDataSource.internalReadCount)
    }

    @Test
    fun whenCleanOneCacheIdThenOtherRemains() = runTest {
        val mainDataSource = MainDataSource({ throw Throwable() }, userRequestValue = true, coroutineContext = testDispatcher)

        assertEquals(DataState.data(requestUseCache.value), mainDataSource.read(requestUseCache).first())
        assertEquals(DataState.data(requestUseCache2.value), mainDataSource.read(requestUseCache2).first())

        mainDataSource.delete(requestUseCache.cacheableId)
        assertEquals(1, mainDataSource.cacheableIds().size)
        assertTrue(mainDataSource.cacheableIds().contains(requestUseCache2.cacheableId))
    }

    @Test
    fun whenClearThenEmpty() = runTest {
        val mainDataSource = MainDataSource({ throw Throwable() }, userRequestValue = true, coroutineContext = testDispatcher)

        assertEquals(DataState.data(requestUseCache.value), mainDataSource.read(requestUseCache).first())
        assertEquals(DataState.data(requestUseCache2.value), mainDataSource.read(requestUseCache2).first())

        mainDataSource.clear()
        assertEquals(0, mainDataSource.cacheableIds().size)
    }
}

data class DataSourceTestData(
    val value: String
)

data class TestDataSourceRequest(
    override val cacheableId: String,
    override val requestType: FlowDataSourceRequest.Type,
    val value: DataSourceTestData
) : FlowDataSourceRequest

class MainDataSource(
    var readFunction: suspend (request: TestDataSourceRequest) -> DataSourceTestData,
    cacheDataSource: FlowDataSource<TestDataSourceRequest, DataSourceTestData>? = null,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    private val userRequestValue: Boolean = false
) :
    BaseFlowDataSource<TestDataSourceRequest, DataSourceTestData>(cacheDataSource, coroutineContext) {

    var internalReadCount = 0

    override suspend fun internalRead(request: TestDataSourceRequest): DataSourceTestData {
        internalReadCount++
        return if (userRequestValue) {
            request.value
        } else {
            readFunction(request)
        }
    }
}

private class CacheDataSource(
    var readFunction: suspend (request: TestDataSourceRequest) -> DataSourceTestData,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : BaseFlowDataSource<TestDataSourceRequest, DataSourceTestData>(coroutineContext = coroutineContext) {
    var internalSaveCount = 0
    var internalDeleteCount = 0
    var deletedCacheableIds = mutableSetOf<Any>()

    override suspend fun internalRead(request: TestDataSourceRequest): DataSourceTestData {
        return if (deletedCacheableIds.contains(request.cacheableId)) {
            throw Throwable("The cache was deleted for this request: ${request.cacheableId}")
        } else {
            readFunction(request)
        }
    }

    override suspend fun save(request: TestDataSourceRequest, data: DataSourceTestData?) {
        internalSaveCount++
    }

    override suspend fun delete(cacheableId: String) {
        internalDeleteCount++
        deletedCacheableIds.add(cacheableId)
        super.delete(cacheableId)
    }
}