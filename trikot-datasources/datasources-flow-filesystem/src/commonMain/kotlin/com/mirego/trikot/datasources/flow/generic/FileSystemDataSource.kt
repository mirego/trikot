package com.mirego.trikot.datasources.flow.generic

import com.mirego.trikot.datasources.flow.BaseExpiringExecutableFlowDataSource
import com.mirego.trikot.datasources.flow.DataSourceDispatchers
import com.mirego.trikot.datasources.flow.ExpiringFlowDataSourceRequest
import com.mirego.trikot.datasources.flow.FlowDataSource
import com.mirego.trikot.datasources.flow.FlowDataSourceExpiringValue
import com.mirego.trikot.datasources.flow.filesystem.NativeFileSystem
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okio.FileNotFoundException
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

open class FileSystemDataSource<R : ExpiringFlowDataSourceRequest, T>(
    private val json: Json,
    private val dataSerializer: KSerializer<T>,
    private val diskCachePath: String,
    private val fileManager: FileSystem = NativeFileSystem.fileSystem,
    cacheDataSource: FlowDataSource<R, FlowDataSourceExpiringValue<T>>? = null,
) : BaseExpiringExecutableFlowDataSource<R, T>(cacheDataSource) {

    public override suspend fun internalRead(request: R): FlowDataSourceExpiringValue<T> {
        if (shouldSkipRequest(request)) {
            throw Throwable("Skipping internal read")
        }

        return withContext(DataSourceDispatchers.IO) {
            val filePath = buildFilePath(request.cacheableId)
            try {
                val data = json.decodeFromString(dataSerializer, getFileAsString(filePath))
                FlowDataSourceExpiringValue(
                    value = data,
                    expiredEpoch = lastModifiedAtMillisMetadata(filePath) + request.expiredInMilliseconds
                )
            } catch (exception: SerializationException) {
                fileManager.deleteRecursively(filePath)
                throw exception
            }
        }
    }

    override suspend fun save(request: R, data: FlowDataSourceExpiringValue<T>?) {
        if (shouldSkipRequest(request)) {
            return
        }

        data?.value?.let { dataToSave ->
            withContext(DataSourceDispatchers.IO) {
                try {
                    val filePath = buildFilePath(request.cacheableId)
                    saveStringValueToFile(filePath, json.encodeToString(dataSerializer, dataToSave))
                } catch (exception: Throwable) {
                    println("Failed to save json to disk cache. Error: $exception")
                }
            }
        }
    }

    override suspend fun delete(cacheableId: String) {
        withContext(DataSourceDispatchers.IO) {
            try {
                val filePath = buildFilePath(cacheableId)
                fileManager.delete(filePath)
            } catch (exception: Throwable) {
                println("Failed to delete file to disk. Error: $exception")
            }
        }
    }

    open fun shouldSkipRequest(request: R): Boolean {
        return false
    }

    private fun getFileAsString(filePath: Path) = if (fileManager.exists(filePath)) {
        fileManager.read(filePath) {
            readUtf8()
        }
    } else {
        throw FileNotFoundException("File at path $filePath does not exist")
    }

    private fun saveStringValueToFile(filePath: Path, value: String) {
        filePath.parent?.let { fileManager.createDirectories(it) }
        fileManager.write(filePath) {
            writeUtf8(value)
        }
    }

    private fun lastModifiedAtMillisMetadata(filePath: Path) = try {
        fileManager.metadata(filePath).lastModifiedAtMillis ?: 0
    } catch (e: FileNotFoundException) {
        0
    }

    private fun buildFilePath(cacheableId: String) = "$diskCachePath/$cacheableId.json".toPath()
}
