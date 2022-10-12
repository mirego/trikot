package com.mirego.trikot.datasources.flow.storage

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.FileNotFoundException
import okio.FileSystem
import okio.Path
import kotlin.coroutines.CoroutineContext

class OkioFlowDataSourceFileManager(
    private val fileSystem: FileSystem,
    private val coroutineContext: CoroutineContext = Dispatchers.Default,
    private val basePath: Path = FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "trikot_data_source"
) : FlowDataSourceFileManager {
    override suspend fun getFileAsString(filename: String) = withContext(coroutineContext) {
        val path = getCachePath(filename)

        if (fileSystem.exists(path)) {
            fileSystem.read(path) {
                readUtf8()
            }
        } else {
            throw FileNotFoundException("File at path $path does not exist")
        }
    }

    override suspend fun saveStringValueToFile(filename: String, value: String) = withContext(coroutineContext) {
        val path = getCachePath(filename)
        path.parent?.let { fileSystem.createDirectories(it) }
        fileSystem.write(getCachePath(filename)) {
            writeUtf8(value)
        }
        Unit
    }

    override suspend fun deleteFile(filename: String) = withContext(coroutineContext) {
        fileSystem.deleteRecursively(getCachePath(filename))
    }

    private fun getCachePath(filename: String): Path =
        basePath / filename
}
