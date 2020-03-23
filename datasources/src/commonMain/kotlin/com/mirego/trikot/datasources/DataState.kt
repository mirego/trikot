package com.mirego.trikot.datasources

@Suppress("EqualsOrHashCode")
sealed class DataState<V, E : Throwable> {
    data class Pending<V, E : Throwable>(val value: V? = null) : DataState<V, E>() {
        override fun hashCode(): Int {
            var result = value?.hashCode() ?: 0
            result = 31 * result + this::class.hashCode()
            return result
        }
    }

    data class Data<V, E : Throwable>(val value: V) : DataState<V, E>() {
        override fun hashCode(): Int {
            var result = value?.hashCode() ?: 0
            result = 31 * result + this::class.hashCode()
            return result
        }
    }

    data class Error<V, E : Throwable>(val error: E, val value: V? = null) : DataState<V, E>() {
        override fun hashCode(): Int {
            var result = error.hashCode()
            result = 31 * result + (value?.hashCode() ?: 0)
            result = 31 * result + this::class.hashCode()
            return result
        }
    }

    companion object {
        fun <V, E : Throwable> pending(value: V? = null): DataState<V, E> = Pending(value)
        fun <V, E : Throwable> data(value: V): DataState<V, E> = Data(value)
        fun <V, E : Throwable> error(error: E, value: V? = null): DataState<V, E> = Error(error, value)
    }

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
