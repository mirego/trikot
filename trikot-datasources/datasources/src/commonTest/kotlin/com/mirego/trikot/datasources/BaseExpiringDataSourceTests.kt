package com.mirego.trikot.datasources

import com.mirego.trikot.datasources.testutils.assertEquals
import com.mirego.trikot.foundation.date.Date
import com.mirego.trikot.streams.reactive.BehaviorSubject
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.promise.Promise
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class BaseExpiringDataSourceTests {

    private val requestUseCache1 = BasicRequest("1", DataSourceRequest.Type.USE_CACHE, 1000)
    private val requestRefreshCache1 = BasicRequest("1", DataSourceRequest.Type.REFRESH_CACHE, 1000)

    @Test
    fun whenCacheIsExpiredThenReadReturnsPendingWithCacheData() {
        val initialData = ExpiringValue(TestData("value"), 0)
        val cacheDataSource = CacheTestDataSource(initialData)
        val readPublisher: BehaviorSubject<ExpiringValue<TestData>> = Publishers.behaviorSubject()
        val readPromise = Promise.from(readPublisher)
        val mainDataSource = TestDataSource(readPromise, cacheDataSource)

        mainDataSource.read(requestUseCache1).assertEquals(DataState.pending(initialData))
    }

    @Test
    fun whenCacheIsValidThenReadReturnsCacheData() {
        val initialData = ExpiringValue(TestData("value"), Date.now.epoch + 1000)
        val cacheDataSource = CacheTestDataSource(initialData)
        val readPublisher: BehaviorSubject<ExpiringValue<TestData>> = Publishers.behaviorSubject()
        val readPromise = Promise.from(readPublisher)
        val mainDataSource = TestDataSource(readPromise, cacheDataSource)

        mainDataSource.read(requestUseCache1).assertEquals(DataState.data(initialData))
    }

    @Test
    fun whenCacheIsExpiredAndNewReadFailedThenErrorWithCacheData() {
        val initialData = ExpiringValue(TestData("value"), 0)
        val cacheDataSource = CacheTestDataSource(initialData)
        val error = Throwable()
        val readPromise = Promise.reject<ExpiringValue<TestData>>(error)
        val mainDataSource = TestDataSource(readPromise, cacheDataSource)

        mainDataSource.read(requestUseCache1).assertEquals(DataState.error(error, initialData))
    }

    @Test
    fun whenCacheIsExpiredThenReadReturnsCacheDataThenReadCompleteWithDataThenNewDataReturns() {
        val initialData = ExpiringValue(TestData("value"), 0)
        val cacheDataSource = CacheTestDataSource(initialData)
        val readPublisher: BehaviorSubject<ExpiringValue<TestData>> = Publishers.behaviorSubject()
        val readPromise = Promise.from(readPublisher)
        val mainDataSource = TestDataSource(readPromise, cacheDataSource)

        val publisher = mainDataSource.read(requestUseCache1)
        publisher.assertEquals(DataState.pending(initialData))
        val newData = ExpiringValue(TestData("new data"), Date.now.epoch + 1000)
        readPublisher.value = newData
        publisher.assertEquals(DataState.data(newData))
    }

    @Test
    fun whenCacheIsValidThenReadWith_REFRESH_CACHE_ReturnsPendingWithData() {
        val initialData = ExpiringValue(TestData("value"), Date.now.epoch + 1000)
        val cacheDataSource = CacheTestDataSource(initialData)
        val readPublisher: BehaviorSubject<ExpiringValue<TestData>> = Publishers.behaviorSubject()
        val readPromise = Promise.from(readPublisher)
        val mainDataSource = TestDataSource(readPromise, cacheDataSource)

        mainDataSource.read(requestRefreshCache1).assertEquals(DataState.pending(initialData))
    }

    private data class TestData(
        val value: String
    )

    private data class BasicRequest(
        override val cacheableId: Any,
        override val requestType: DataSourceRequest.Type,
        override val expiredInMilliseconds: Long
    ) : ExpiringDataSourceRequest

    private class TestDataSource(private val readPromise: Promise<ExpiringValue<TestData>>, cacheDataSource: CacheTestDataSource? = null) :
        BaseExpiringDataSource<BasicRequest, TestData, ExpiringValue<TestData>>(cacheDataSource) {

        var internalReadCount = 0

        override fun internalRead(request: BasicRequest): Promise<ExpiringValue<TestData>> {
            internalReadCount++
            return readPromise
        }

        override fun save(request: BasicRequest, data: ExpiringValue<TestData>?) {
        }
    }

    private class CacheTestDataSource(private var savedValue: ExpiringValue<TestData>? = null) :
        BaseExpiringDataSource<BasicRequest, TestData, ExpiringValue<TestData>>() {

        override fun internalRead(request: BasicRequest): Promise<ExpiringValue<TestData>> {
            return savedValue?.let { Promise.resolve(it) } ?: Promise.reject(Throwable())
        }

        override fun save(request: BasicRequest, data: ExpiringValue<TestData>?) {
            savedValue = data
        }
    }
}
