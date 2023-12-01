package com.mirego.trikot.datasources.flow.generic

import com.mirego.trikot.datasources.flow.BaseExpiringExecutableFlowDataSource
import com.mirego.trikot.datasources.flow.DataSourceDispatchers
import com.mirego.trikot.datasources.flow.ExpiringFlowDataSourceRequest
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

class FileSystemDataSource<R : ExpiringFlowDataSourceRequest, T>(
    private val json: Json,
    private val dataSerializer: KSerializer<T>,
    private val diskCachePath: String,
    private val fileManager: FileSystem = NativeFileSystem.fileSystem
) : BaseExpiringExecutableFlowDataSource<R, T>() {

    public override suspend fun internalRead(request: R): FlowDataSourceExpiringValue<T> {
        return withContext(DataSourceDispatchers.IO) {
            val filePath = buildFilePath(request)
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
        data?.value?.let { dataToSave ->
            withContext(DataSourceDispatchers.IO) {
                try {
                    val filePath = buildFilePath(request)
                    saveStringValueToFile(filePath, json.encodeToString(dataSerializer, dataToSave))
                } catch (exception: Throwable) {
                    println("Failed to save json to disk cache. Error: $exception")
                }
            }
        }
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

    private fun buildFilePath(request: R) = "$diskCachePath/${request.cacheableId}.json".toPath()
}
