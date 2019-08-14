package com.mirego.trikot.datasources

import org.reactivestreams.Publisher

interface DataSource<R : DataSourceRequest, T> {
    fun read(request: R): Publisher<DataSourceState<T>>
    fun save(request: R, data: T?)
}
