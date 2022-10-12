package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.DataState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class BaseDataSourceTests {
    private val requestUseCache = TestDataSourceRequest("1", FlowDataSourceRequest.Type.USE_CACHE, DataSourceTestData("1"))
    private val requestRefreshCache = TestDataSourceRequest("1", FlowDataSourceRequest.Type.REFRESH_CACHE, DataSourceTestData("1"))
    private val requestUseCache2 = TestDataSourceRequest("2", FlowDataSourceRequest.Type.USE_CACHE, DataSourceTestData("2"))
    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun whenCacheHasDataThenReadReturnsCacheDataAndInternalReadNotCalled() = runTest {
        val initialData = DataSourceTestData("value")
        val cacheDataSource = CacheDataSource({ initialData }, testDispatcher)
        val mainDataSource = MainDataSource({ DataSourceTestData("otherValue") }, cacheDataSource, testDispatcher)

        assertEquals(DataState.data(initialData), mainDataSource.read(requestUseCache).first())
        assertEquals(0, mainDataSource.internalReadCount)
    }

    @Test
    fun whenCacheIsValidThenReadWith_REFRESH_CACHE_ReturnsPendingWithDataAndInternalReadCalled() = runTest {
        val initialData = DataSourceTestData("value")
        val cacheDataSource = CacheDataSource({ initialData })
        val mainDataSource = MainDataSource({ DataSourceTestData("otherValue") }, cacheDataSource)
        assertEquals(DataState.pending(initialData), mainDataSource.read(requestUseCache).first())
        assertEquals(1, mainDataSource.internalReadCount)
    }
//
//    @Test
//    fun givenPendingCacheDataWhenCacheDataCompletedThenReturningPendingWithCacheDataAndInternalReadCalled() {
//        val cacheReadPublisher: BehaviorSubject<DataSourceTestData> = Publishers.behaviorSubject()
//        val cacheDataSource = CacheDataSource(Promise.from(cacheReadPublisher))
//        val mainDataSource = MainDataSource({ DataSourceTestData("otherValue") }, cacheDataSource)
//
//        val publisher = mainDataSource.read(requestRefreshCache)
//        publisher.assertEquals(DataState.pending())
//        val cacheData = DataSourceTestData("value")
//        cacheReadPublisher.value = cacheData
//        publisher.assertEquals(DataState.pending(cacheData))
//        assertEquals(1, mainDataSource.internalReadCount)
//    }
//
//    @Test
//    fun givenPendingCacheDataWhenReadWithRefreshAndCacheDataCompletedAndDataIsAvailableThenReturningTheNewDataAndSaveCalled() {
//        val cacheReadPublisher: BehaviorSubject<DataSourceTestData> = Publishers.behaviorSubject()
//        val cacheDataSource = CacheDataSource(Promise.from(cacheReadPublisher))
//        val newData = DataSourceTestData("newValue")
//        val mainDataSource = MainDataSource(Promise.resolve(newData), cacheDataSource)
//
//        val publisher = mainDataSource.read(requestRefreshCache)
//        publisher.assertEquals(DataState.pending())
//        val cacheData = DataSourceTestData("value")
//        cacheReadPublisher.value = cacheData
//        publisher.assertEquals(DataState.data(newData))
//        assertEquals(1, mainDataSource.internalReadCount)
//        assertEquals(1, cacheDataSource.internalSaveCount)
//    }
//
//    @Test
//    fun when3ReadAtTheSameTimeThenInternalReadCalledOnlyOnce() {
//        val initialData = DataSourceTestData("value")
//        val cacheDataSource = CacheDataSource(Promise.resolve(initialData))
//        val mainDataSource = MainDataSource({ DataSourceTestData("otherValue") }, cacheDataSource)
//
//        mainDataSource.read(requestRefreshCache).assertEquals(DataState.pending(initialData))
//        mainDataSource.read(requestRefreshCache).assertEquals(DataState.pending(initialData))
//        mainDataSource.read(requestRefreshCache).assertEquals(DataState.pending(initialData))
//        assertEquals(1, mainDataSource.internalReadCount)
//    }
//
//    @Test
//    fun givenCacheDataWhen2ReadAreMadeThenSamePublisherIsReturned() {
//        val initialData = DataSourceTestData("value")
//        val cacheDataSource = CacheDataSource(Promise.resolve(initialData))
//        val mainDataSource = MainDataSource({ DataSourceTestData("otherValue") }, cacheDataSource)
//
//        val publisher1 = mainDataSource.read(requestRefreshCache)
//        val publisher2 = mainDataSource.read(requestRefreshCache)
//        assertSame(publisher1, publisher2)
//    }
//
//    @Test
//    fun givenCachedDataWhenRefreshingWithoutAnySubscriberThenNextSubscriberReceiveRefreshedData() {
//        val initialData = DataSourceTestData("value")
//        val cacheDataSource = CacheDataSource(Promise.resolve(initialData))
//        val mainDataSource = MainDataSource({ DataSourceTestData("otherValue") }, cacheDataSource)
//
//        mainDataSource.read(requestUseCache)
//        val refreshData = DataSourceTestData("refreshValue")
//        mainDataSource.readPromise = Promise.resolve(refreshData)
//        mainDataSource.read(requestRefreshCache)
//        mainDataSource.read(requestUseCache).assertEquals(DataState.data(refreshData))
//    }
//
//    @Test
//    fun whenRefreshingWithCachedDataWhenAnErrorOccursThenCachedDataIsReturnedWithAnError() {
//        val data = DataSourceTestData("data")
//        val mainDataSource = MainDataSource(Promise.resolve(data))
//
//        mainDataSource.read(requestUseCache).assertEquals(DataState.data(data))
//        val error = Throwable()
//        mainDataSource.readPromise = Promise.reject(error)
//        mainDataSource.read(requestRefreshCache).assertEquals(DataState.error(error, data))
//    }

    @Test
    fun whenNoCacheDataSourceThenStartInPendingThenDataReceived() = runTest {
        val readFlow = MutableSharedFlow<DataSourceTestData>()
        val mainDataSource = MainDataSource({ readFlow.single() })

        val publisher = mainDataSource.read(requestRefreshCache)
        assertEquals(DataState.Pending(), publisher.first())
        val data = DataSourceTestData("data")
        readFlow.emit(data)
        assertEquals(DataState.data(data), publisher.first())
    }

    @Test
    fun when2ReadFromDifferentCacheIdThenDataIsDistinctAndInternalReadCalledTwice() = runTest {
        val mainDataSource = MainDataSource({ throw Throwable() }, userRequestValue = true)

        assertEquals(DataState.data(requestUseCache.value), mainDataSource.read(requestUseCache).first())
        assertEquals(DataState.data(requestUseCache.value), mainDataSource.read(requestUseCache2).first())
        assertEquals(2, mainDataSource.internalReadCount)
    }

    @Test
    fun whenDeleteCacheIdThenInternalReadCalled() = runTest {
        val initialData = DataSourceTestData("value")
        val cacheDataSource = CacheDataSource({ initialData })
        val mainDataSource = MainDataSource({ DataSourceTestData("otherValue") }, cacheDataSource)

        assertEquals(DataState.data(initialData), mainDataSource.read(requestUseCache).first())

        mainDataSource.delete(requestUseCache.cacheableId)

        assertEquals(DataState.pending(), mainDataSource.read(requestUseCache).first())

        assertEquals(1, cacheDataSource.internalDeleteCount)
        assertEquals(1, mainDataSource.internalReadCount)
    }

    @Test
    fun whenCleanOneCacheIdThenOtherRemains() = runTest {
        val mainDataSource = MainDataSource({ throw Throwable() }, userRequestValue = true)

        assertEquals(DataState.data(requestUseCache.value), mainDataSource.read(requestUseCache).first())
        assertEquals(DataState.data(requestUseCache.value), mainDataSource.read(requestUseCache2).first())

        mainDataSource.delete(requestUseCache.cacheableId)
        assertEquals(1, mainDataSource.cacheableIds().size)
        assertTrue(mainDataSource.cacheableIds().contains(requestUseCache2.cacheableId))
    }

    @Test
    fun whenClearThenEmpty() = runTest {
        val mainDataSource = MainDataSource({ throw Throwable() }, userRequestValue = true)

        assertEquals(DataState.data(requestUseCache.value), mainDataSource.read(requestUseCache).first())
        assertEquals(DataState.data(requestUseCache.value), mainDataSource.read(requestUseCache2).first())

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
    private var readFunction: suspend (request: TestDataSourceRequest) -> DataSourceTestData,
    cacheDataSource: FlowDataSource<TestDataSourceRequest, DataSourceTestData>? = null,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    private val userRequestValue: Boolean = false
) :
    BaseFlowDataSource<TestDataSourceRequest, DataSourceTestData>(cacheDataSource, coroutineContext) {

    var internalReadCount = 0

    override suspend fun internalRead(request: TestDataSourceRequest): DataSourceTestData {
        internalReadCount++
        return readFunction(request)
    }
}

class CacheDataSource(
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