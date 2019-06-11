package com.mirego.trikot.metaviews

import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.resource.Font
import com.mirego.trikot.metaviews.text.RichText
import org.reactivestreams.Publisher

interface MetaLabel : MetaView {
    /**
     * Label text
     */
    val text: Publisher<String>

    /**
      * Label rich text. Use this instead of {@link #text text} when not null
     */
    val richText: Publisher<RichText>?
    /**
     * Label font. FontManager should be configured by platform.
     */
    val fontFace: Publisher<Font>
    /**
     * Label font size
     */
    val fontSize: Publisher<Int>
    /**
     * Label text color
     */
    val textColor: Publisher<MetaSelector<Color>>
}
