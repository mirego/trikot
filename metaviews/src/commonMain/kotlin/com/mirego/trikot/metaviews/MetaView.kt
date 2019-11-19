package com.mirego.trikot.metaviews

import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.properties.MetaAction
import org.reactivestreams.Publisher

interface MetaView {
    /**
     * Alpha value of the view. 0.0 to 1.0
     */
    val alpha: Publisher<Float>
    /**
     * Background color of the view
     */
    val backgroundColor: Publisher<MetaSelector<Color>>
    /**
     * Is view hidden or not. Should not take any place in the UI.
     * Should respect View.GONE Android behavior
     */
    val hidden: Publisher<Boolean>
    /**
     * Action to execute when the view is tapped
     */
    val onTap: Publisher<MetaAction>
}
