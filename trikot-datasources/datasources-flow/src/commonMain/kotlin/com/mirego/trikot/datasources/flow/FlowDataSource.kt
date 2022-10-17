package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.DataState
import kotlinx.coroutines.flow.Flow

interface FlowDataSource<R : FlowDataSourceRequest, T> {
    /**
     * Send a read request to the Datasource
     */
    fun read(request: R): Flow<DataState<T, Throwable>>

    /**
     * Save data to the datasource
     */
    suspend fun save(request: R, data: T?)

    /**
     * Delete data in the datasource
     */
    suspend fun delete(cacheableId: String)

    /**
     * Clear all data in the datasource
     */
    suspend fun clear()
}
