package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.DataState
import com.mirego.trikot.datasources.flow.storage.FlowDataSourceFileManager
import com.mirego.trikot.datasources.flow.tests.MockFileManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DiskCacheFlowDataSourceTests {
    private companion object {
        private const val BASE_PATH = "testBasePath"
    }

    private val json = Json
    private val testDispatcher = UnconfinedTestDispatcher()

    private fun buildDataSource(
        fileManager: FlowDataSourceFileManager,
        deleteOnSerializationError: Boolean = true
    ) = DiskCacheFlowDataSource<BasicRequest, TestData>(
        json,
        TestData.serializer(),
        fileManager,
        basePath = BASE_PATH,
        coroutineContext = testDispatcher,
        deleteOnSerializationError = deleteOnSerializationError
    )

    @Test
    fun testRead_withValidJson() = runTest {
        val fileManager = MockFileManager("{\"value\":\"value\"}")
        val diskCacheDataFlow = buildDataSource(fileManager)
        val readResult = diskCacheDataFlow.read(BasicRequest("abc", FlowDataSourceRequest.Type.REFRESH_CACHE)).first()
        assertEquals(listOf("$BASE_PATH/abc"), fileManager.getFileAsStringCalls)
        assertEquals(0, fileManager.deleteFileCalls.size)
        val data: DataState.Data<TestData, Throwable> = assertIs(readResult)
        assertEquals("value", data.value.value)
    }

    @Test
    fun testRead_withInvalidJsonAndDeleteOnSerializationError_callsDeleteOnSerializationError() = runTest {
        val fileManager = MockFileManager("{\"value\":\"value")
        val diskCacheDataFlow = buildDataSource(fileManager, deleteOnSerializationError = true)
        val readResult = diskCacheDataFlow.read(BasicRequest("abc", FlowDataSourceRequest.Type.REFRESH_CACHE)).first()
        val error: DataState.Error<TestData, Throwable> = assertIs(readResult)
        assertIs<SerializationException>(error.error)
        assertEquals(listOf("$BASE_PATH/abc"), fileManager.deleteFileCalls)
    }

    @Test
    fun testRead_withInvalidJsonAndDeleteOnSerializationError_callsDoesntCallDeleteOnSerializationError() = runTest {
        val fileManager = MockFileManager("{\"value\":\"value")
        val diskCacheDataFlow = buildDataSource(fileManager, deleteOnSerializationError = false)
        val readResult = diskCacheDataFlow.read(BasicRequest("abc", FlowDataSourceRequest.Type.REFRESH_CACHE)).first()
        val error: DataState.Error<TestData, Throwable> = assertIs(readResult)
        assertIs<SerializationException>(error.error)
        assertEquals(0, fileManager.deleteFileCalls.size)
    }

    @Test
    fun testSave() = runTest {
        val fileManager = MockFileManager("")
        val diskCacheDataFlow = buildDataSource(fileManager)
        diskCacheDataFlow.save(BasicRequest("abc", FlowDataSourceRequest.Type.REFRESH_CACHE), TestData("value"))
        assertEquals(listOf("$BASE_PATH/abc" to "{\"value\":\"value\"}"), fileManager.saveStringValueToFileCalls)
    }

    @Test
    fun testDelete() = runTest {
        val fileManager = MockFileManager("")
        val diskCacheDataFlow = buildDataSource(fileManager)
        diskCacheDataFlow.delete("abc")
        assertEquals(listOf("$BASE_PATH/abc"), fileManager.deleteFileCalls)
    }

    @Test
    fun testClean() = runTest {
        val fileManager = MockFileManager("")
        val diskCacheDataFlow = buildDataSource(fileManager)
        diskCacheDataFlow.clear()
        assertEquals(listOf(BASE_PATH), fileManager.deleteFileCalls)
    }

    @Serializable
    private data class TestData(val value: String)

    private data class BasicRequest(
        override val cacheableId: String,
        override val requestType: FlowDataSourceRequest.Type
    ) : FlowDataSourceRequest
}
