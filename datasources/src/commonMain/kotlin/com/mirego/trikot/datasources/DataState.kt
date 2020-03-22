package com.mirego.trikot.datasources

@Suppress("EqualsOrHashCode")
sealed class DataState<V, E : Throwable> {
    data class Pending<V, E : Throwable>(val value: V? = null) : DataState<V, E>() {
        override fun hashCode() = disambiguatedHashCode()
    }

    data class Data<V, E : Throwable>(val value: V) : DataState<V, E>() {
        override fun hashCode() = disambiguatedHashCode()
    }

    data class Error<V, E : Throwable>(val error: E, val value: V? = null) : DataState<V, E>() {
        override fun hashCode() = disambiguatedHashCode()
    }

    /**
     * As opposed to equals, sealed class implementation with identical properties
     * can share the same hashCode, this circumvents it
     */
    fun disambiguatedHashCode() = 31 * this::class.hashCode() + super.hashCode()

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
