package com.mirego.trikot.datasources

import org.reactivestreams.Publisher

interface DataSource<R : DataSourceRequest, T> {
    /**
     * Send a read request to the Datasource
     */
    fun read(request: R): Publisher<DataState<T, Throwable>>

    /**
     * Save data to the datasource
     */
    fun save(request: R, data: T?)

    /**
     * Delete data in the datasource
     */
    fun delete(cacheableId: Any)
}
