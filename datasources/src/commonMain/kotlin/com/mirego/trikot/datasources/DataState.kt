package com.mirego.trikot.datasources

sealed class DataState<V, E : Throwable> {
    class Pending<V, E : kotlin.Error>(val value: V? = null) : DataState<V, E>()
    class Data<V, E : kotlin.Error>(val value: V) : DataState<V, E>()
    class Error<V, E : kotlin.Error>(val error: E, val value: V? = null) : DataState<V, E>()

    fun isPending(ignoreData: Boolean = true): Boolean {
        return when (val result = this) {
            is Pending -> if (ignoreData) true else result.value == null
            else -> false
        }
    }

    fun isError(ignoreData: Boolean = true): Boolean {
        return when (val result = this) {
            is Error -> if (ignoreData) true else result.value == null
            else -> false
        }
    }

    fun hasData(ignorePending: Boolean = true, ignoreError: Boolean = true): Boolean {
        return when (val result = this) {
            is Pending -> if (ignorePending) result.value != null else false
            is Data -> true
            is Error -> if (ignoreError) result.value != null else false
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
