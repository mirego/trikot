package com.mirego.trikot.analytics

import com.mirego.trikot.metaviews.properties.MetaAction
import com.mirego.trikot.metaviews.properties.MetaActionBlock
import com.mirego.trikot.streams.reactive.just
import org.reactivestreams.Publisher

class MetaTrackableAction(private val event: AnalyticsEvent, private val properties: Publisher<AnalyticsPropertiesType> = mapOf<String, Any>().just(), actionBlock: MetaActionBlock) : MetaAction(actionBlock) {
    constructor(event: AnalyticsEvent, properties: AnalyticsPropertiesType, actionBlock: MetaActionBlock) : this(event, properties.just(), actionBlock)

    override fun execute(actionContext: Any?) {
        super.execute(actionContext)
        AnalyticsConfiguration.analyticsManager.trackEvent(event, properties)
    }
}
