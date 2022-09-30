package com.mirego.trikot.analytics

import com.mirego.trikot.streams.reactive.promise.Promise

class EmptyAnalyticsService : AnalyticsService {
    override val name: String = "ANALYTICS SERVICE NOT CONFIGURED"

    override var isEnabled = false

    override val distinctId: Promise<String?> = Promise.resolve("ANALYTICS_SERVICE_NOT_CONFIGURED_DISTINCT_ID")

    override fun identifyUser(userId: String, properties: AnalyticsPropertiesType) {
    }

    override fun logout() {
    }

    override fun incrementUserProperties(incrementalProperties: AnalyticsIncrementalProperties) {
    }

    override fun setSuperProperties(properties: AnalyticsPropertiesType) {
    }

    override fun setUserProperties(properties: AnalyticsPropertiesType) {
    }

    override fun unsetSuperProperties(propertyNames: List<String>) {
    }

    override fun unsetAllSuperProperties() {
    }

    override fun trackEvent(event: AnalyticsEvent, properties: AnalyticsPropertiesType) {
    }
}
