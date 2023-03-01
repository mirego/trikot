package com.mirego.trikot.http

enum class CachePolicy {
    /**
     * Use cached value if present
     */
    USE_PROTOCOL_CACHE_POLICY,

    /**
     * Skip cache, force data reload
     */
    RELOAD_IGNORING_CACHE_DATA
}
