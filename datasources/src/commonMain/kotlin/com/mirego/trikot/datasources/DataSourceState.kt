package com.mirego.trikot.datasources

data class DataSourceState<T>(val isLoading: Boolean, val data: T?, val error: Throwable? = null)
