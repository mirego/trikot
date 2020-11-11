package com.mirego.trikot.datasources.extensions

import com.mirego.trikot.datasources.DataState

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
