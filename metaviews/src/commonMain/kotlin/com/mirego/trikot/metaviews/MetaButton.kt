package com.mirego.trikot.metaviews

import com.mirego.trikot.metaviews.properties.Alignment
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.resource.ImageResource
import org.reactivestreams.Publisher

interface MetaButton : MetaLabel {
    /**
     * Resource for the background image of the button
     */
    val backgroundImageResource: Publisher<MetaSelector<ImageResource>>
    /**
     * If the button is enabled or disabled
     */
    val enabled: Publisher<Boolean>
    /**
     * Position of the image related to the text
     */
    val imageAlignment: Publisher<Alignment>
    /**
     * Ressource of the button image. Can be associated with a text
     */
    val imageResource: Publisher<MetaSelector<ImageResource>>
    /**
     * Selected state of the view
     */
    val selected: Publisher<Boolean>
    /**
     * ImageResource tint color
     */
    val tintColor: Publisher<MetaSelector<Color>>
}
