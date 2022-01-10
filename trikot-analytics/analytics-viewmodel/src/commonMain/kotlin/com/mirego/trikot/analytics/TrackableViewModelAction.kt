package com.mirego.trikot.analytics

import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.mirego.trikot.viewmodels.properties.ViewModelActionBlock
import org.reactivestreams.Publisher

open class TrackableViewModelAction(private val event: AnalyticsEvent, private val properties: Publisher<AnalyticsPropertiesType> = mapOf<String, Any>().just(), actionBlock: ViewModelActionBlock) : ViewModelAction(actionBlock) {
    constructor(event: AnalyticsEvent, properties: AnalyticsPropertiesType, actionBlock: ViewModelActionBlock) : this(event, properties.just(), actionBlock)

    override fun execute(actionContext: Any?) {
        super.execute(actionContext)
        AnalyticsConfiguration.analyticsManager.trackEvent(event, properties)
    }
}
