package com.mirego.trikot.datasources.flow.generic

import com.mirego.trikot.datasources.flow.ExpiringFlowDataSourceRequest
import com.mirego.trikot.datasources.flow.FlowDataSourceExpiringValue
import com.mirego.trikot.datasources.flow.FlowDataSourceRequest
import com.mirego.trikot.datasources.flow.filesystem.NativeFileSystem
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okio.FileNotFoundException
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class FileSystemDataSourceTests {
    private val diskCachePath = "testDiskCachePath"
    private val json = Json
    private val dataSerializer = TestData.serializer()

    private val fileSystem = FakeFileSystem()
    private val fileSystemDataSource = FileSystemDataSource<BasicRequest, TestData>(json, dataSerializer, diskCachePath, fileSystem)
    private val fileSystemDataSourceSkipAll = SkipAllFileSystemDataSource<BasicRequest, TestData>(json, dataSerializer, diskCachePath, fileSystem)

    @Test
    fun test_read_valid_data() = runTest {
        val testRequest = BasicRequest("test", 1000L, FlowDataSourceRequest.Type.REFRESH_CACHE)
        val testData = TestData("value")
        val testDataJson = "{\"value\": \"value\"}"
        val filePath = "$diskCachePath/${testRequest.cacheableId}.json".toPath()

        val parentDirectory = filePath.parent ?: throw AssertionError("Parent directory should not be null")
        fileSystem.createDirectories(parentDirectory)
        fileSystem.write(filePath) {
            writeUtf8(testDataJson)
        }

        val result = fileSystemDataSource.internalRead(testRequest)

        assertEquals(testData, result.value)
    }

    @Test
    fun test_read_nonexistent_file() = runTest {
        val testRequest = BasicRequest("nonexistent", 1000L, FlowDataSourceRequest.Type.REFRESH_CACHE)

        assertFailsWith<FileNotFoundException> {
            fileSystemDataSource.internalRead(testRequest)
        }
    }

    @Test
    fun test_save_data() = runTest {
        val testRequest = BasicRequest("saveTest", 1000L, FlowDataSourceRequest.Type.REFRESH_CACHE)
        val testData = TestData("savedValue")
        val filePath = "$diskCachePath/${testRequest.cacheableId}.json".toPath()

        fileSystemDataSource.save(testRequest, FlowDataSourceExpiringValue(testData, Clock.System.now().toEpochMilliseconds()))

        assertTrue(fileSystem.exists(filePath))
        val savedContent = fileSystem.read(filePath) {
            readUtf8()
        }
        assertEquals("{\"value\":\"savedValue\"}", savedContent)
    }

    @Test
    fun test_read_skip() = runTest {
        val testRequest = BasicRequest("test", 1000L, FlowDataSourceRequest.Type.REFRESH_CACHE)
        val testDataJson = "{\"value\": \"value\"}"
        val filePath = "$diskCachePath/${testRequest.cacheableId}.json".toPath()

        val parentDirectory = filePath.parent ?: throw AssertionError("Parent directory should not be null")
        fileSystem.createDirectories(parentDirectory)
        fileSystem.write(filePath) {
            writeUtf8(testDataJson)
        }

        assertFails {
            fileSystemDataSourceSkipAll.internalRead(testRequest)
        }
    }

    @Test
    fun test_save_data_skip() = runTest {
        val testRequest = BasicRequest("saveTest", 1000L, FlowDataSourceRequest.Type.REFRESH_CACHE)
        val testData = TestData("savedValue")
        val filePath = "$diskCachePath/${testRequest.cacheableId}.json".toPath()

        fileSystemDataSourceSkipAll.save(testRequest, FlowDataSourceExpiringValue(testData, Clock.System.now().toEpochMilliseconds()))

        assertFalse(fileSystem.exists(filePath))
    }

    @Serializable
    private data class TestData(val value: String)

    private data class BasicRequest(
        override val cacheableId: String,
        override val expiredInMilliseconds: Long = 0,
        override val requestType: FlowDataSourceRequest.Type
    ) : ExpiringFlowDataSourceRequest

    private class SkipAllFileSystemDataSource<R : ExpiringFlowDataSourceRequest, TestData>(
        json: Json,
        dataSerializer: KSerializer<TestData>,
        diskCachePath: String,
        fileManager: FileSystem = NativeFileSystem.fileSystem,
    ) : FileSystemDataSource<R, TestData>(json, dataSerializer, diskCachePath, fileManager) {
        override fun shouldSkipRequest(request: R): Boolean {
            return true
        }
    }
}
