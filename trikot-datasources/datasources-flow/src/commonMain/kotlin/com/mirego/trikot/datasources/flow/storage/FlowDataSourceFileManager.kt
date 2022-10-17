package com.mirego.trikot.datasources.flow.storage

interface FlowDataSourceFileManager {
    suspend fun getFileAsString(filename: String): String
    suspend fun saveStringValueToFile(filename: String, value: String)
    suspend fun deleteFile(filename: String)
}
