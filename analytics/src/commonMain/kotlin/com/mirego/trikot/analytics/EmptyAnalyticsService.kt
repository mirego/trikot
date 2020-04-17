package com.mirego.trikot.analytics

class EmptyAnalyticsService : AnalyticsService {
    override val name: String = "ANALYTICS SERVICE NOT CONFIGURED"

    override fun identifyUser(userId: String, properties: AnalyticsPropertiesType) {
    }

    override fun logout() {
    }

    override fun incrementUserProperties(incrementalProperties: AnalyticsIncrementalProperties) {
    }

    override fun setSuperProperties(properties: AnalyticsPropertiesType) {
    }

    override fun unsetSuperProperties(propertyNames: List<String>) {
    }

    override fun unsetAllSuperProperties() {
    }

    override fun trackEvent(event: AnalyticsEvent, properties: AnalyticsPropertiesType) {
    }
}
