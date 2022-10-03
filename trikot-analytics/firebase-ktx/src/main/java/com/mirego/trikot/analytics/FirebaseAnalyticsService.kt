package com.mirego.trikot.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.mirego.trikot.streams.reactive.promise.Promise

class FirebaseAnalyticsService(context: Context, analyticsEnabled: Boolean = true) :
    AnalyticsService {

    private var firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    private var analyticsEnabled: Boolean = analyticsEnabled

    override var isEnabled: Boolean
        get() = analyticsEnabled
        set(value) {
            analyticsEnabled = value
            firebaseAnalytics.setAnalyticsCollectionEnabled(value)
        }

    override val name: String = "FirebaseAnalytics"

    private var superProperties = emptyMap<String, Any>()

    override fun distinctAppId() = Promise.create<String> { resolve, reject ->
        firebaseAnalytics.appInstanceId
            .addOnCompleteListener { task ->
                val result = task.result
                if (task.isSuccessful && result != null) {
                    resolve(result)
                } else if(!task.isSuccessful) {
                    reject(Throwable(task.exception))
                } else {
                    reject(Throwable("Empty result"))
                }
            }
            .addOnCanceledListener {
                reject(Throwable("Canceled"))
            }
    }

    override fun identifyUser(userId: String, properties: AnalyticsPropertiesType) {
        firebaseAnalytics.setUserId(userId)
        properties.forEach {
            firebaseAnalytics.setUserProperty(it.key, it.value.toString())
        }
    }

    // This functionality isn't supported with Firebase Analytics
    override fun incrementUserProperties(incrementalProperties: AnalyticsIncrementalProperties) =
        Unit

    override fun logout() {
        firebaseAnalytics.setUserId(null)
    }

    override fun setSuperProperties(properties: AnalyticsPropertiesType) {
        superProperties = superProperties + properties
    }

    override fun setUserProperties(properties: AnalyticsPropertiesType) {
        properties.forEach {
            firebaseAnalytics.setUserProperty(it.key, it.value.toString())
        }
    }

    override fun unsetSuperProperties(propertyNames: List<String>) {
        superProperties = superProperties.entries
            .filter { !propertyNames.contains(it.key) }
            .fold(emptyMap()) { acc, value ->
                acc + value.toPair()
            }
    }

    override fun unsetAllSuperProperties() {
        superProperties = emptyMap()
    }

    override fun trackEvent(event: AnalyticsEvent, properties: AnalyticsPropertiesType) {
        val allProperties = properties + superProperties
        val bundle = Bundle()
        allProperties.forEach {
            bundle.putString(it.key, it.value.toString())
        }
        firebaseAnalytics.logEvent(event.name, bundle)
    }
}
