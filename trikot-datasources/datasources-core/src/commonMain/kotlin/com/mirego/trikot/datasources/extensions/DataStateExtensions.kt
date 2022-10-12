package com.mirego.trikot.datasources.extensions

import com.mirego.trikot.datasources.DataState

@Deprecated(
    "Use value() instead",
    replaceWith = ReplaceWith("value()")
)
fun <T, E : Throwable> DataState<T, E>.data(): T? = value()

fun <T, E : Throwable> DataState<T, E>.error(): E? =
    when (this) {
        is DataState.Error -> this.error
        else -> null
    }

fun <T, R, E : Throwable> DataState<T, E>.mapData(block: (T) -> R): DataState<R, E> =
    when (this) {
        is DataState.Pending -> DataState.Pending(value?.let(block))
        is DataState.Data -> DataState.Data(block(value))
        is DataState.Error -> DataState.Error(error, value?.let(block))
    }

fun <T, E : Throwable> DataState<T, E>.value(): T? {
    return when (val state = this) {
        is DataState.Pending -> state.value
        is DataState.Data -> state.value
        is DataState.Error -> state.value
    }
}

inline fun <T, R> T.runCatchingDataState(block: T.() -> R): DataState<R, Throwable> =
    try {
        DataState.data(block())
    } catch (t: Throwable) {
        DataState.error(t)
    }

fun isAnyDataStatePending(vararg dataStates: DataState<*, Throwable>) =
    dataStates.any {
        it is DataState.Pending
    }

fun isAnyDataStateError(vararg dataStates: DataState<*, Throwable>) =
    dataStates.any {
        it is DataState.Error
    }

fun getFirstError(vararg dataStates: DataState<*, Throwable>) =
    dataStates.firstNotNullOf {
        it.error()
    }