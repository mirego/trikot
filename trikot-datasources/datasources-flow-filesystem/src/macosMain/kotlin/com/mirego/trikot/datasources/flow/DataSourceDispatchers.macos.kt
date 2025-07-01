package com.mirego.trikot.datasources.flow

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext

@OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
internal actual object DataSourceDispatchers {
    actual val IO = newSingleThreadContext("IO").limitedParallelism(100)
}
