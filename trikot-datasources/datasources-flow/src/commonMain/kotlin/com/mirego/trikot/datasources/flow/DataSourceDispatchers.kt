package com.mirego.trikot.datasources.flow

import kotlinx.coroutines.CoroutineDispatcher

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object DataSourceDispatchers {
    val IO: CoroutineDispatcher
}
