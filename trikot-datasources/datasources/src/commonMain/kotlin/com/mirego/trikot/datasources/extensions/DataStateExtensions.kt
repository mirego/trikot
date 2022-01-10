package com.mirego.trikot.datasources.extensions

import com.mirego.trikot.datasources.DataState
import com.mirego.trikot.streams.reactive.processors.MapProcessorBlock

fun <T, E : Throwable> DataState<T, E>.data(): T? =
    when (this) {
        is DataState.Pending -> this.value
        is DataState.Data -> this.value
        is DataState.Error -> this.value
    }

fun <T, E : Throwable> DataState<T, E>.error(): E? =
    when (this) {
        is DataState.Error -> this.error
        else -> null
    }

fun <T, R, E : Throwable> DataState<T, E>.mapData(block: MapProcessorBlock<T, R>): DataState<R, E> =
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
