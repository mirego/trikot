package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.flow.storage.FlowDataSourceFileManager
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class DiskCacheFlowDataSource<R : FlowDataSourceRequest, T : Any>(
    private val json: Json,
    private val dataSerializer: KSerializer<T>,
    private val fileManager: FlowDataSourceFileManager,
    cachedDataSource: FlowDataSource<R, T>? = null,
    private val basePath: String = dataSerializer.descriptor.serialName,
    private val deleteOnSerializationError: Boolean = true,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : BaseFlowDataSource<R, T>(cachedDataSource, coroutineContext) {

    override suspend fun internalRead(request: R): T =
        try {
            json.decodeFromString(dataSerializer, fileManager.getFileAsString(getPath(request.cacheableId)))
        } catch (e: SerializationException) {
            if (deleteOnSerializationError) {
                fileManager.deleteFile(getPath(request.cacheableId))
            }
            throw e
        }

    override suspend fun save(request: R, data: T?) {
        super.save(request, data)
        data?.let { dataToSave ->
            fileManager.saveStringValueToFile(getPath(request.cacheableId), json.encodeToString(dataSerializer, dataToSave))
        }
    }

    override suspend fun delete(cacheableId: String) {
        super.delete(cacheableId)
        fileManager.deleteFile(getPath(cacheableId))
    }

    override suspend fun clear() {
        super.clear()
        fileManager.deleteFile(basePath)
    }

    private fun getPath(cacheableId: String) = "$basePath/$cacheableId"
}
