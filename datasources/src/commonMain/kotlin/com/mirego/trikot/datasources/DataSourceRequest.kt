package com.mirego.trikot.datasources

interface DataSourceRequest {
    val cachableId: Any
    val requestType: Type

    enum class Type {
        USE_CACHE,
        REFRESH_CACHE
    }
}
