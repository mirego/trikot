package com.mirego.trikot.datasources.flow.tests

import com.mirego.trikot.datasources.flow.storage.FlowDataSourceFileManager

class MockFileManager(
    private val fileContent: String
) : FlowDataSourceFileManager {
    internal val getFileAsStringCalls = mutableListOf<String>()
    internal val saveStringValueToFileCalls = mutableListOf<Pair<String, String>>()
    internal val deleteFileCalls = mutableListOf<String>()

    override suspend fun getFileAsString(filename: String): String {
        getFileAsStringCalls.add(filename)
        return fileContent
    }

    override suspend fun saveStringValueToFile(filename: String, value: String) {
        saveStringValueToFileCalls.add(filename to value)
    }

    override suspend fun deleteFile(filename: String) {
        deleteFileCalls.add(filename)
    }
}