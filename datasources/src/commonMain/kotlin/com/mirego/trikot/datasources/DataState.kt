package com.mirego.trikot.datasources

sealed class DataState<V, E : Throwable> {
    class Pending<V, E : Throwable>(val value: V? = null) : DataState<V, E>()
    class Data<V, E : Throwable>(val value: V) : DataState<V, E>()
    class Error<V, E : Throwable>(val error: E, val value: V? = null) : DataState<V, E>()

    fun isPending(pendingIfDataIsAvailable: Boolean = true): Boolean {
        return when (val result = this) {
            is Pending -> pendingIfDataIsAvailable || result.value == null
            else -> false
        }
    }

    fun isError(errorIfDataIsAvailable: Boolean = true): Boolean {
        return when (val result = this) {
            is Error -> errorIfDataIsAvailable || result.value == null
            else -> false
        }
    }

    fun hasData(hasDataIfPending: Boolean = true, hasDataIfError: Boolean = true): Boolean {
        return when (val result = this) {
            is Pending -> hasDataIfPending && result.value != null
            is Data -> true
            is Error -> hasDataIfError && result.value != null
            else -> false
        }
    }
}
