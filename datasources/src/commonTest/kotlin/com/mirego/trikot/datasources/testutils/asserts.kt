package com.mirego.trikot.datasources.testutils

import com.mirego.trikot.datasources.DataState
import kotlin.test.assertTrue

fun <V, E : Throwable> DataState<V, E>.assertValue(expectedValue: V) =
    assertTrue { this is DataState.Data && value == expectedValue }

fun <V, E : Throwable> DataState<V, E>.assertError(expectedError: E, expectedValue: V? = null) =
    assertTrue { this is DataState.Error && error == expectedError && value == expectedValue }

fun <V, E : Throwable> DataState<V, E>.assertPending(expectedValue: V? = null) =
    assertTrue { this is DataState.Pending && value == expectedValue }
