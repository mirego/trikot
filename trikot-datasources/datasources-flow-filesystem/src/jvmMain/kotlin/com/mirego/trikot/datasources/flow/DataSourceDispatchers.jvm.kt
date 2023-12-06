package com.mirego.trikot.datasources.flow

import kotlinx.coroutines.Dispatchers

internal actual object DataSourceDispatchers {
    actual val IO = Dispatchers.IO
}
