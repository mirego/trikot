package com.mirego.trikot.analytics

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.first
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher

typealias AnalyticsPropertiesType = Map<String, Any>
typealias AnalyticsIncrementalProperties = Map<String, Number>

interface AnalyticsService {
    val name: String

    /*
    Enables/Disables the analytics collection
     */
    var isEnabled: Boolean

    /*
    userId: Id of the logged in user
    properties: Properties associated with the user
     */
    fun identifyUser(userId: String, properties: AnalyticsPropertiesType)

    /*
    UnIdentify the current logged in user
    */
    fun logout()

    /*
    properties: Add the given amount to an existing property on the identified user.
    (Use negative increment to decrement properties)
     */
    fun incrementUserProperties(incrementalProperties: AnalyticsIncrementalProperties)

    /*
    properties: Properties that will be added to all event tracked
     */
    fun setSuperProperties(properties: AnalyticsPropertiesType)

    /*
    properties: Properties that will be added to the identified user profile
    */
    fun setUserProperties(properties: AnalyticsPropertiesType)

    /*
    properties: Remove some super properties
     */
    fun unsetSuperProperties(propertyNames: List<String>)

    /*
    properties: Remove all super properties
     */
    fun unsetAllSuperProperties()

    /*
    event: Event to track, will be store using toString() method
    properties: Properties to add with the event
     */
    fun trackEvent(event: AnalyticsEvent, properties: AnalyticsPropertiesType)

    companion object : AnalyticsService {
        private fun currentAnalyticsService() = AnalyticsConfiguration.analyticsManager

        override val name = currentAnalyticsService().name

        override var isEnabled: Boolean
            get() = currentAnalyticsService().isEnabled
            set(value) {
                currentAnalyticsService().isEnabled = value
            }

        override fun identifyUser(userId: String, properties: AnalyticsPropertiesType) {
            currentAnalyticsService().identifyUser(userId, properties)
        }

        override fun logout() {
            currentAnalyticsService().logout()
        }

        override fun incrementUserProperties(incrementalProperties: AnalyticsIncrementalProperties) {
            currentAnalyticsService().incrementUserProperties(incrementalProperties)
        }

        override fun setSuperProperties(properties: AnalyticsPropertiesType) {
            currentAnalyticsService().setSuperProperties(properties)
        }

        override fun setUserProperties(properties: AnalyticsPropertiesType) {
            currentAnalyticsService().setUserProperties(properties)
        }

        override fun unsetSuperProperties(propertyNames: List<String>) {
            currentAnalyticsService().unsetSuperProperties(propertyNames)
        }

        override fun unsetAllSuperProperties() {
            currentAnalyticsService().unsetAllSuperProperties()
        }

        override fun trackEvent(event: AnalyticsEvent, properties: AnalyticsPropertiesType) {
            currentAnalyticsService().trackEvent(event, properties)
        }
    }
}

/*
event: Event to track, will be store using toString() method
properties: Publisher of property to store with the event. Publisher MUST return a value.
 */
fun AnalyticsService.trackEvent(
    event: AnalyticsEvent,
    properties: Publisher<AnalyticsPropertiesType>
) {
    properties.first().subscribe(CancellableManager()) {
        trackEvent(event, it)
    }
}

/*
 userId: Id of the logged in user
 properties: Publisher of property to store with the event. Publisher MUST return a value.
 */
fun AnalyticsService.identifyUser(userId: String, properties: Publisher<AnalyticsPropertiesType>) {
    properties.first().subscribe(CancellableManager()) {
        identifyUser(userId, it)
    }
}

/*
UnIdentify the current logged in user
*/
fun AnalyticsService.logout(unsetAllSuperProperties: Boolean = false) {
    logout()
    if (unsetAllSuperProperties) {
        unsetAllSuperProperties()
    }
}
