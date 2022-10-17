package com.mirego.trikot.datasources.flow

interface FlowDataSourceRequest {
    /**
     * Identifier use to save data to cache
     */
    val cacheableId: String
    /**
     * Type of request (See DataSourceRequest.Type)
     */
    val requestType: Type

    enum class Type {
        /**
         * Use cache if available
         */
        USE_CACHE,
        /**
         * Refresh data and save to cache
         */
        REFRESH_CACHE
    }
}
