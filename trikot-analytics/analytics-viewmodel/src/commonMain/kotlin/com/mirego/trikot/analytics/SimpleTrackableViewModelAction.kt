package com.mirego.trikot.analytics

import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.mirego.trikot.viewmodels.properties.ViewModelActionBlock

open class SimpleTrackableViewModelAction(private val event: AnalyticsEvent, private val properties: AnalyticsPropertiesType = mapOf(), actionBlock: ViewModelActionBlock) :
    ViewModelAction(actionBlock) {

    override fun execute() {
        super.execute()
        AnalyticsConfiguration.analyticsManager.trackEvent(event, properties)
    }

    override fun execute(actionContext: Any?) {
        super.execute(actionContext)
        AnalyticsConfiguration.analyticsManager.trackEvent(event, properties)
    }
}
