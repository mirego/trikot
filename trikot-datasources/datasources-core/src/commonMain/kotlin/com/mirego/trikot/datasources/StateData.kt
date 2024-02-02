package com.mirego.trikot.datasources

typealias StateData<V> = DataState<V, Throwable>

fun <V> stateDataPending(value: V? = null): StateData<V> = DataState.pending(value)
fun <V> stateDataData(value: V): StateData<V> = DataState.data(value)
fun <V> stateDataError(error: Throwable, value: V? = null): StateData<V> = DataState.error(error, value)
