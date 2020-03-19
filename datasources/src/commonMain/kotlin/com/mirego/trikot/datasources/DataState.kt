package com.mirego.trikot.datasources

sealed class DataState<V, E : Throwable> {
    class Pending<V, E : Throwable>(val value: V? = null) : DataState<V, E>()
    class Data<V, E : Throwable>(val value: V) : DataState<V, E>()
    class Error<V, E : Throwable>(val error: E, val value: V? = null) : DataState<V, E>()

    fun isPending(ignoreData: Boolean = true): Boolean {
        return when (val result = this) {
            is Pending -> ignoreData || result.value == null
            else -> false
        }
    }

    fun isError(ignoreData: Boolean = true): Boolean {
        return when (val result = this) {
            is Error -> ignoreData || result.value == null
            else -> false
        }
    }

    fun hasData(ignorePending: Boolean = true, ignoreError: Boolean = true): Boolean {
        return when (val result = this) {
            is Pending -> ignorePending && result.value != null
            is Data -> true
            is Error -> ignoreError && result.value != null
            else -> false
        }
    }

    val data: V?
        get() = when (val result = this) {
            is Pending -> result.value
            is Data -> result.value
            is Error -> result.value
        }
}
