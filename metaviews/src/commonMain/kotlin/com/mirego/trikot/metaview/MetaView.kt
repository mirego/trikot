package com.mirego.trikot.metaview

import com.mirego.trikot.metaview.properties.Color
import com.mirego.trikot.metaview.properties.MetaSelector
import com.mirego.trikot.metaview.properties.MetaAction
import org.reactivestreams.Publisher

interface MetaView {
    /**
     * Alpha value of the view. 0 - 1
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
     * Android only, determine if the ripple effect will have borders
     */
    val isHighlightedBorderless: Publisher<Color>
    /**
     * Action to execute when the user tap the view
     */
    val onTap: Publisher<MetaAction>
}
