package com.mirego.trikot.analytics

import com.mirego.trikot.foundation.concurrent.AtomicReference

class AnalyticsServiceLogger(private val analyticsService: AnalyticsService) : AnalyticsService {
    override val name: String = "AnalyticsServiceLogger"

    private val superProperties = AtomicReference<AnalyticsPropertiesType>(mapOf())

    override fun identifyUser(userId: String, properties: AnalyticsPropertiesType) {
        println("""
Analytics - Identify user (Service: ${analyticsService.name})
  userId: $userId
${properties.toLog()}
        """.trimMargin())
        analyticsService.identifyUser(userId, properties)
    }

    override fun logout() {
        println("Analytics - Logout (Service: ${analyticsService.name})")
        analyticsService.logout()
    }

    override fun incrementUserProperties(incrementalProperties: AnalyticsIncrementalProperties) {
        println("""
Analytics - Increment user properties (Service: ${analyticsService.name})
Incremented Properties: ${incrementalProperties.toLog()}
        """.trimMargin())

        analyticsService.incrementUserProperties(incrementalProperties)
    }

    override fun setSuperProperties(properties: AnalyticsPropertiesType) {
        val newValue = superProperties.value + properties
        updateSuperProperties(newValue, "setSuperProperties")
        analyticsService.setSuperProperties(properties)
    }

    override fun unsetSuperProperties(propertyNames: List<String>) {
        val newValues = superProperties.value.entries.filter { !propertyNames.contains(it.key) }.fold(emptyMap<String, Any>()) { acc, value ->
            acc + value.toPair()
        }
        updateSuperProperties(newValues, "unsetSuperProperties")
        analyticsService.unsetSuperProperties(propertyNames)
    }

    override fun unsetAllSuperProperties() {
        val newValue = mapOf<String, Any>()
        updateSuperProperties(newValue, "unsetAllSuperProperties")
        analyticsService.unsetAllSuperProperties()
    }

    override fun trackEvent(event: AnalyticsEvent, properties: AnalyticsPropertiesType) {
        println("""
Analytics - Track event (Service: ${analyticsService.name})
  event: $event
${properties.toLog()}
${superProperties.value.toLog()}
        """.trimMargin())
        analyticsService.trackEvent(event, properties)
    }

    private fun AnalyticsPropertiesType.toLog(prefix: String = "  "): String {
        return entries.map { "$prefix${it.key}: ${it.value}\n" }.joinToString(" ")
    }

    private fun updateSuperProperties(newProps: AnalyticsPropertiesType, methodName: String) {
        superProperties.setOrThrow(superProperties.value, newProps)
        println("""
Analytics - SuperProperties updated with $methodName (Service: ${analyticsService.name})
${newProps.toLog()}
        """.trimMargin())
    }
}
