package com.mirego.trikot.analytics

import com.mirego.trikot.foundation.concurrent.AtomicReference

object AnalyticsConfiguration {
    private val internalAnalyticsService = AtomicReference<AnalyticsService>(EmptyAnalyticsService())

    var analyticsManager: AnalyticsService
        get() = internalAnalyticsService.value
        set(value) { internalAnalyticsService.compareAndSet(internalAnalyticsService.value, value) }
}
