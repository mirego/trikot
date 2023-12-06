package com.mirego.trikot.datasources.flow

import kotlinx.coroutines.newSingleThreadContext

internal actual object DataSourceDispatchers {
    actual val IO = newSingleThreadContext("IO").limitedParallelism(100)
}
