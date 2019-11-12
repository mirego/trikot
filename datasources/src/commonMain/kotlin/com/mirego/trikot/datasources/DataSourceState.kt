package com.mirego.trikot.datasources

data class DataSourceState<T>(val isLoading: Boolean, val data: T?, val error: Throwable? = null) {
    fun <R> mapData(lambda: (T?) -> R?): DataSourceState<R> {
        return DataSourceState(isLoading, lambda(data), error)
    }

    companion object {
        fun <T> dataLoaded(data: T): DataSourceState<T> {
            return DataSourceState(false, data)
        }

        fun <T> loading(): DataSourceState<T> {
            return DataSourceState(true, null)
        }

        fun <T> withError(error: Throwable): DataSourceState<T> {
            return DataSourceState(false, null, error)
        }
    }
}
